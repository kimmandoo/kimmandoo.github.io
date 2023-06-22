---
categories: [Error]
tags: [android, error]
---

## 에러 배경

```bash
Execution failed for task ':domain:compileKotlin'.
> 'compileJava' task (current target is 1.8) and 'compileKotlin' task (current target is 17) jvm target compatibility should be set to the same Java version.
  Consider using JVM toolchain: https://kotl.in/gradle/jvm/toolchain

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.

```

멀티모듈 도입중 domain레이어를 Java, Kotlin Library로 만들었는데 compileJava와 compileKotlin의 버전 차이가 난다는 오류가 나옴.



처음에는 domain의 빌드 파일에서 java가 1_7로 돼있길래 1_8로 바꿔줬는데도 위 오류가 계속 나옴

```
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
```

문제는 kotlin쪽이다.

```groovy
kotlinOptions {
        jvmTarget = '1.8'
    }
```

JDK 1.1버전부터는 1.를 붙이지않고 서브버전표시를 하는데 저 메시지에 따라 현재 버전인 17로 바꿔주면 된다. 

```groovy
compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
kotlinOptions {
        jvmTarget = '17'
    }
```


이렇게 모든 빌드파일(build.gradle)의 버전을 다 맞춰 주니 무사히 빌드가 됐다.



---

> 참조: 
> 
> 