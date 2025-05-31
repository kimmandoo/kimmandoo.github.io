---
categories: [KMP]
tags: [android, kotlin, kmp]
mermaid: true
---

# Splash Screen, Launch Screen

사이드를 안드로이드로만 할까~ 하다가 KMP로 틀었다. 어차피 혼자 진행하는 프로젝트이기도 하고, 매번 똑같은 작업만 반복하기 보다는 요즘 급부상중인 Compose Multi Platform을 제대로 해보고 싶었기 때문이다.

composeApp을 중심으로 ios, android 모두 jetpack compose로 ui를 그리고, 비즈니스로직을 kotlin으로 작성하는 프레임워크다. 물론 네이티브 종속적인 부분들을 따로 swift를 써서 구현해줘야하지만 이제 ios에서도 cmp가 stable 버전으로 올라오게 됐으므로 지금이 트렌드에 올라탈 기회라고 생각했다!

새로운 기술을 배울 때는 역시 clone coding이 최고라고 생각하기 때문에, udmey에서 강의를 하나 구매했다.

kmp프로젝트는 아직 android studio에서 완전히 지원하지않아서, jetbrain에서 제공하는 wizard를 써야된다. spring이 생각나는 구조다.

> https://kmp.jetbrains.com/?android=true&ios=true&iosui=compose&includeTests=true

## Splash Screen - Android

안드로이드는 app launch 때 나오는 로고화면 같은 걸 splash screen이라고 부른다. 원래는 handler같은거로 구현했는데, 공식지원하는 api가 나와서 이제는 그걸 사용한다.

```toml
splash-screen = { module = "androidx.core:core-splashscreen" , version.ref ="1.0.1" } 
```

splash screen을 디자인할 때는, 240x240의 정사각형 틀과, 그 안에 160x160의 원을 기준으로 만들면 된다. 

> https://developer.android.com/develop/ui/views/launch/splash-screen?hl=ko

적용방법은 간단하다. splash를 위한 style파일을 만들고, 그걸 매니페스트에 선언하고, LaunchActivity가 onCreate되기전에 install해주면 된다.

먼저 `splash.xml` 파일을 보자.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style parent="Theme.SplashScreen" name="Theme.NutriSport.Splash">
        <item name="windowSplashScreenBackground">#FFFFFF</item>
        <item name="postSplashScreenTheme">@android:style/Theme.Material.Light.NoActionBar</item>
				<!--This theme will apply after splash screen-->
        <item name="windowSplashScreenAnimatedIcon">@drawable/splash_logo</item>
    </style>
</resources>
```

Theme.SplashScreen을 사용하려면 위에 적어둔 splashscreen api의 dependency를 추가해야된다. 로고 뒤에 나올 화면을 설정하고, 어떤 아이콘을 보여줄지 정해준다. 

아이콘 이미지는 되도록 vector asset으로 하는 걸 추천한다.

이제 postSplashScreenTheme을 이해해야되는데, 간단하다. 스플래시 스크린이 뜨고나서, 그 이후에 적용될 Theme을 고르라는 의미다. 그래서 여기에는 기본이 될 Theme을 지정해주면 된다.

`AndroidManifest.xml`에도 적용해준다.

```xml
<application
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:roundIcon="@mipmap/ic_launcher_round"
    android:supportsRtl="true"
    android:theme="@style/Theme.NutriSport.Splash">
```

아까 splash style 파일 만들었을 때, name으로 지정한 Theme을 넣어주면된다. 그 다음, LaunchActivity의 onCreate, setContent전에 install해주면 된다. 

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        // splash screen은 setContent전에 있어야됨
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}
```

이러면 정말 간단하게 splash screen을 구현한 것이다. install 이후에 animation을 넣어서 splash 스크린 속도를 이용해 전처리 작업도 할 수 있는데, 그건 나중에 구현해보도록 하겠다. 

## Launch Screen - IOS

ios에서는 splash screen을 launch screen이라고 부른다. kmp프로젝트에서 iosApp이 있는데, 이걸 Xcode로 열어주면서 시작한다. 아직 Xcode와 cocoapod을 설치하지않았다면 먼저 설치하고 오면 된다.

ios는 역시 까탈스럽게 뭔가 많다. 먼저 아까 만들던 로고 규격보다 훨씬 크게 만들어서 png로 뽑아준다. 1080x1080으로 했다.

![img](/assets/img/post/0529/1.png){: width="400" height="400" }

먼저 Asset의 New Image set을 추가하고, single scale과 mode 선택을 해준다. 디자이너가 있다면 각 조합 9개의 모든 디자인을 다 다르게 할 수도 있겠지만 1인 개발은 그런거 없다.

![img](/assets/img/post/0529/2.png)

이미지를 3개 다 넣어줘야한다. 같은거 넣어도 상관없다.

![img](/assets/img/post/0529/3.png)

그 다음에 file from template으로 launch screen을 만들면 된다. ios앱을 타겟으로 잡고 만들면 된다. 만약 Xcode를 설치하고 ios support를 설치하지않았으면 지금이라도 설치해야한다.

![img](/assets/img/post/0529/4.png)

imageview를 먼저 추가해서 가운데로 위치시키고,  아까 만든 이미지를 추가해주면 된다. 여기서 느낀점. Xcode는 진짜 애플스러운 IDE다. 미려하고, UI가 단순하지만 막상 사용하려면 쉽지않은 그런 애플스러운 감성을 가지고 있다는 생각이 들었다.

Background는 system background로 하면 알아서 흰색 검은색 지원이 된다.

![img](/assets/img/post/0529/5.png)

여기서 끝난게 아니라 사이즈 조절도 해주고, Constraint도 정해줘야한다...

![img](/assets/img/post/0529/6.png)

아까 만든 launch screen을 Target 세팅에서도 연결해준다. 진짜 끝이없다.

확장자까지 들어가있어서 `.storyboard`라고 되어있다. 이렇게 되면 LaunchScreen이 제대로 동작하지않는다. LaunchScreen.storyboard가 아니라 그냥 LaunchScreen으로만 남겨주자.

이러면 splash screen, launch screen 세팅이 끝난다. 뭐 하지도 않았는데 진빠지는 시간이었다.
