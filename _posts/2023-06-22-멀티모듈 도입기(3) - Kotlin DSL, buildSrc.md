---
categories: [Android]
tags: [android, KotlinDSL]
---

현재 멀티모듈로 구성한 프로젝트의 빌드는 groovy dsl로 되어있다. 이걸 kotlin dsl로 바꾸는 작업을 할건데, 그 이유는 kotlin이 groovy보다 익숙한 언어이고 컴파일시간확인에도 편하기 때문이다.

KTS, Kotlin DSL이라고도 하는데 비슷하지만 다르다고 할 수 있는데,

- KTS: 코틀린 스크립트로 작성한 gradle 빌드 구성파일

- KotlinDSL: KTS와 동일한 뜻으로 사용되거나 기본 Gradle Kotlin DSL

KTS가 좀 더 큰 범위로 보인다. groovy에서 이전하는 관점에서는 동일한 의미가 맞다. 이 포스팅에서는 kts로 통칭하겠다. 

kts파일은 `.gradle.kts`확장자를 사용한다.

## KotlinDSL 적용기

확장자를 바꿔서 사용할 것이기 때문에 groovy에서 미리 전처리를 해줘야 편하다. groovy는 메서드 호출시 괄호를 생략할 수 있지만 코틀린은 괄호`( )`를 쳐야한다.

또 속성 할당시 `=` 생략하던걸 코틀린에선 넣어줘야한다.

```groovy
compileSdkVersion 30
compileSdkVersion(30)

java {
    sourceCompatibility JavaVersion.VERSION_17
    targetCompatibility JavaVersion.VERSION_17
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
```

문자열 표현에도 차이가 있는데 무조건 큰따옴표`""`를 사용해야하고 $접두사로 사용하는 표현식은 중괄호로 묶어야한다.

```kotlin
myRootDirectory = "${project.rootDir}/tools/proguard-rules-debug.pro"
```

이 문법 바꿔 놓는데서 노가다라 시간이 많이 걸렸다. 공식문서에서 원본을 백업해두라고해서 따로 패키지를 파서 넣어두었다.

Hilt적용할 때 추가했던 ext.hilt_version이 공식문서에는 조치방법이 없었는데, stackoverflow에 바로 있었다.

```kotlin
extra.apply{
        set("hilt_version","2.44")
    }
    val hilt_version = extra.get("hilt_version") as String
```

ext로 줄여져있던걸 extra로 맵핑해서 사용하는 방식이다. 

def로 정의된 변수는 val로 바꿔주고, 불리언 속성은 is를 접두어로 붙여준다.

```kotlin
getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            ...
        }
```

plugins 블럭이 제일 말썽이라고 생각한다. 바꿀게 워낙 많기 때문인데, `version`, `apply` 키워드 사이에 있는 것들은 놔둬도 되고, `id`, `version` 사이에 있는 걸 괄호로 묶고 큰따옴표로 바꾼다. 

```kotlin
apply(plugin = "java")
```

이렇게 단일로 하는 경우도 있겠으나 원래 있던 걸 바꾸는 과정이니 바꿀 때 마다 rebuild를 해주면서 오류가 나나 안나나 확인해봐야한다.

## buildSrc

일일이 다 바꾸고 나면, 버전관리를 한곳에서 하고 싶다는 생각이 막 들기 시작한다. 이때 필요한게 buildSrc모듈이다.

buildSrc모듈을 java/kotlin 라이브러리 모듈로 생성하고, setting.gradle에서 지워준다. 그 후 buildSrc의 build.gradle.kts파일에 아래 코드를 넣어준다.

```kotlin
import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}
```

이 모듈에서 앱레벨, 라이브러리, 버전관리를 할 것이기 때문에 object 파일 2개를 만들어준다.

나는 AppConfig과 Dependencies파일 2개로 나눴다.



AppConfig은 앱레벨, Dependencies는 라이브러리 버전, 종속성 관리 용도다.

예시로 보여주자면, Dependencies파일 내부는 이렇게 생겼다.

```kotlin
object Versions {
    // 라이브러리 버전 관리
    const val KOTLINX_COROUTINE = "1.7.1"
}

object Libraries {
    // 라이브러리 종속성 관리
    object KotlinX {
        const val KOTLINX_COROUTINE = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINE}"
    }
}
```

이걸 앞으로 각 모듈의 빌드파일에 아래와 같이 연결해주면된다. Libraries대신에 Dependencies로 하려고했는데 이미 예약어여서 충돌우려가 있어 변경했다.

presentation모듈의 build.gradle.kts다.

```kotlin
implementation(Libraries.KotlinX.KOTLINX_COROUTINE)
```

---

> 참조: 
> 
> https://developer.android.com/studio/build/migrate-to-kts?hl=ko
> 
> https://stackoverflow.com/questions/69323109/unresolved-reference-ext-when-convert-gradle-to-gradle-kts
> 
> https://docs.gradle.org/nightly/userguide/migrating_from_groovy_to_kotlin_dsl.html#applying_plugins