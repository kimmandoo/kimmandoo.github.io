---
categories: [KMP]
tags: [android, kotlin, kmp]
mermaid: true
---

# KMP에서 GoogleLogin 도전하기

firebase auth와, gcp의 기능을 사용해서 google one-tap login을 구현해보자. 일단 3rd party 라이브러리로 간단하게 구현하고, 해당 라이브러리를 쓰지않고 구현하는 것 까지 해보겠다.

google login을 구현할 때, 제일 먼저 메모해둬야하는 건 oauth clientId다.

![img](assets/img/post/0531/1.png){: width="400" height="400" }

firebase에서 authentication을 활성화 시키고 제공업체로 Google을 선택하면 볼 수 있는 창이다. 근데 이건 webclientId이기 때문에, 앱 전용 oauth client id를 쓰려면 새로 만들어줘야한다. ios, android 모두 필요하지만 일단 android만 만든다. 

![img](assets/img/post/0531/2.png){: width="400" height="400" }

이걸 gcp에 들어가서 확인하면 위 창이 나오는데, oauth client id를 android용으로 새로 만들어주자. 

![img](assets/img/post/0531/3.png){: width="400" height="400" }

새로 만드려면 package 명과, sha-1 fingerprint 2개가 필요하다. package명은 어렵게 생각할 필요없이 app의 진입점으로 사용하고 있는 모듈의 applicationId를 보면 된다. 보통은 default package가 applicationId로 설정되기 때문에 똑같을 수 있는데, 멀티모듈을 구성하면서 바뀌었을 수도 있다. 그래서 gradle 파일에서 확인하는 게 확실하다.

sha-1은 gradle 명령어로 signingReport를 입력하면 추출 할 수 있다. 이건 gcp로 clientid를 만드는 방식인데, 원래 firebase에서 android 앱추가를 하면 알아서 만들어준다.

clientId도 얻었으니 바로 구현 들어가겠다. KMP AUTH라는 라이브러리를 사용해서 먼저 구현해볼 것이다. 

> https://github.com/mirzemehdi/KMPAuth

```kotlin
GoogleButtonUiContainerFirebase(
    linkAccount = false,
    onResult = { res ->
        // success or failure msg
        res.onSuccess { user ->
                println(user)
                messageBarState.addSuccess("Login Success")
                loadingState = true
            }
            .onFailure { e ->
                if (e.message?.contains("network error") == true){
                    messageBarState.addError("Network Error")
                }else if(e.message?.contains("idToken is null") == true){
                    messageBarState.addError("Invalid Token")
                }else{
                    messageBarState.addError(e.message ?: "Unknown Error")
                }
                loadingState = false
            }
    }
) {
    GoogleButton(
        loading = loadingState,
        onClick = {
            this@GoogleButtonUiContainerFirebase.onClick()
        })
}
```

라이브러리를 만든 분이 기본적으로 Ui까지 제공해주지만, 이미 만든 ui가 있다면 UiContainerScope에 CustomUi를 넣어서 처리하면 된다. onClick이벤트만 가로채서 위로 던져주면, 그때부터는 라이브러리와 동일하게 동작하는 것이다. 지금 코드에는 강사분이 자체제작하신 messageBar라는 라이브러리를 쓰는데, kmp용으로 만든 Snackbar느낌이다.

```kotlin
@Composable
@Preview
fun App() {
    var appReady by remember { mutableStateOf(false) }
    MaterialTheme {
        LaunchedEffect(Unit){
            GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = Constants.WEB_CLIENT_ID))
            appReady = true
        }

        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(), // 이상하게 날아오는 거 싫으면 이렇게
            visible = appReady
        ){
            SetupNavGraph()
        }
    }
}
```

composeApp 진입점에서 webclient id를 받아 GoogleAuthProvider를 활성화시켜주는 작업도 필요하다. 

```shell
FATAL EXCEPTION: main (Ask Gemini)
Process: com.kimmandoo.nutrisport, PID: 4283
java.lang.IllegalArgumentException: Make sure you invoked GoogleAuthProvider #create method with providing credentials
	at com.mmk.kmpauth.google.GoogleAuthProvider$GoogleAuthProviderImpl.get(GoogleAuthProvider.kt:56)
	at com.mmk.kmpauth.google.GoogleAuthProvider$Companion.get$kmpauth_google_debug(GoogleAuthProvider.kt:27)
	at com.mmk.kmpauth.google.GoogleButtonUiContainerKt.GoogleButtonUiContainer(GoogleButtonUiContainer.kt:44)
	at com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebaseKt.GoogleButtonUiContainerFirebase(GoogleButtonUiContainerFirebase.kt:45)
```

auth를 사용하는 스크린을 보여주기 전에 GoogleAuthProvider가 활성화되어있지않으면 crash가 발생한다. 그래서 appReady변수로 제어해줬다. LaunchedEffect 컴포저블이 실행되고 나서 appReady변수가 true로 바뀌니까 빠른 접근 때문에 생기는 버그는 잡을 수 있다.

단순히 이거로 끝나면 좋겠지..만! FirebaseApp initialization문제는 아직 끝나지 않았다.

```shell
Default FirebaseApp failed to initialize because no default options were found. This usually means that com.google.gms:google-services was not applied to your gradle project.
```
google-services 플러그인을 빼먹으면 이런게 생기니까, plugin에 제대로 추가해줘야한다.

그리고 Android App을 실행할 때, ApplicationClass에서 Firebase를 최초에 초기화 해줘야한다.

```kotlin
class NutriApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(context = this)
    }
}
```

매니페스트 파일에서 name도 지정해주는 걸 빼먹으면 안된다. 이때 사용하는 라이브러리로는 gitlive의 firebase를 사용했다.

이렇게 해도 안된다! 하는 사람은 먼저 3가지를 확인해보자.

1. android manifest파일에 INTERNET permission을 허용했는지?
2. google-services.json 파일을 제대로 넣어뒀는지? -> authentication을 적용하고나서 새로 받아야한다.
3. 실기기를 사용하는 경우, 기기의 인터넷이 켜져있는지?

아래 단계정도만 진행하고 ios는 다음 게시물에서 작성하겠다. 강의대로 따라했더니 ios는 빌드가 안된다!

ios는 요구하는 단계가 안드로이드보다 좀 있다. 

먼저 firebase에서 새 앱 추가로 ios를 선택해 만들어준다.

![img](assets/img/post/0531/4.png){: width="400" height="400" }

번들 id는 위에서 확인할 수 있다.

```text
PRODUCT_BUNDLE_IDENTIFIER=com.kimmandoo.nutrisport.nutri-sport$(TEAM_ID)
```
이렇게 되어있는데, TEAM_ID를 지금 당장 정하지 않았으면 $(TEAM_ID)앞까지만 쓰면 된다.

![img](assets/img/post/0531/5.png){: width="400" height="400" }

ios용 패키지를 받아준다. 그러면 이제 아래처럼 코드를 작성할 수 있다. 패키지를 받을 때, firebase 관련 패키지들을 직접 iosApp에 달아줘야 오류를 막을 수 있다.

```swift
@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    var body: some Scene {
        WindowGroup {
            ContentView().onOpenURL(perform: { url in
                GIDSignIn.sharedInstance.handle(url)
            })
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    
    func application(
        _ app: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        return true
    }
// GIDSignIn에서 처리를 해주기 때문에 굳이 필요없어졌다.
//    func application(
//      _ app: UIApplication,
//      open url: URL,
//      options: [UIApplication.OpenURLOptionsKey : Any] = [:]
//    ) -> Bool {
//      var handled: Bool
//
//      handled = GIDSignIn.sharedInstance.handle(url)
//      if handled {
//        return true
//      }
//
//      // Handle other custom URL types.
//
//      // If not handled by this app, return false.
//      return false
//    }
}
```

onOpenURL 부분을 통해 url내에서 이벤트 처리가 가능해졌다. swift와 ios개발은 아예 하나도 몰라서 이 부분은 다시 공부해봐야한다.

```xml
<key>GIDServerClientID</key>
<string>YOUR_SERVER_CLIENT_ID</string>

<key>GIDClientID</key>
<string>YOUR_IOS_CLIENT_ID</string>
<key>CFBundleURLTypes</key>
<array>
  <dict>
    <key>CFBundleURLSchemes</key>
    <array>
      <string>YOUR_DOT_REVERSED_IOS_CLIENT_ID</string>
    </array>
  </dict>
</array>
```

plist를 입력할 때, 입력을 아무리 해도 빈칸으로 들어갈 때가 있을 것이다. 그럴 땐 그냥 안드로이드 스튜디오에서 편집하거나 하면 된다.
위에서 구현한 코드는 SwiftUI와 Firebase연동에서 발생하는 초기화 타이밍 문제가 있었다. 이걸 해결하기 위해서는 AppDelegate의 init에서 FirebaseApp.configure()를 처리하면 되는데, 간단하다.

```swift
class AppDelegate: NSObject, UIApplicationDelegate {
    
    override init() {
        super.init()
        FirebaseApp.configure()
    }
    
    func application(
        _ app: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
//        FirebaseApp.configure()
        return true
    }
}
```

@UIApplicationDelegateAdaptor를 써도 View가 delegate보다 먼저 초기화될 수 있는데, 그때 AppDelegate가 완료되기 전에 Firebase 관련 메서드에 접근하면 바로 에러난다. 또한 SwiftUI가 앱 라이프사이클을 UIKit처럼 1:1로 맞춰주지 않기 때문에, 위에 코드처럼 아예 init블록에서 처리되게 하면 안전하다.

```text
Undefined symbol: _kfun:androidx.compose.material3#androidx_compose_material3_MaterialTheme$stableprop_getter$artificial(){}kotlin.Int
```

이런 로그가 발생했는데, 아무리 봐도 서드파티 라이브러리 때문인 것 같다!

## fix: 20250601

원인을 찾았다. 답은 stackoverflow에 있었다.

https://stackoverflow.com/questions/79616622/xcode-fails-to-run-kotlin-multiplatform-project-after-upgrading-compose-to-1-8-0

```text
Starting with Compose Multiplatform 1.8.0, the UI framework fully transitioned to the K2 compiler. So, to share UI code using the latest Compose Multiplatform you should:

use at least Kotlin 2.1.0 for your projects,

depend on libraries based on Compose Multiplatform only if they are compiled against at least Kotlin 2.1.0.

As a workaround until all dependencies are updated, you may turn off Gradle cache by adding kotlin.native.cacheKind=none to your gradle.properties file. This will increase compilation time.
```

compse multiplatform 버전이 1.8.0으로 올라가면서, UI 프레임워크가 K2컴파일러로 전환됐는데 이거 때문에 sharedUI환경에서 오류가 발생하는 것이었다. gradle 캐싱과정중에 문제가 발생하는 것으로 보이는데, 아래 코드를 gradle.properties파일에 추가하면 해결되는 문제다.

`kotlin.native.cacheKind=none`

아직은 임시방편이라 나중에 수정되는 걸 기다려야겠다. 사용하는 라이브러리가 K2컴파일러로 빌드되는 과정에서 뭔가 문제가 발생하는 걸로 추정중이다...

KMP, 특히 Kotlin/Native(iOS, Desktop 등)는 라이브러리(.klib) 바이너리 간의 심볼/메타데이터 호환성이 중요하다. K2에서 빌드된 .klib 파일과 K1에서 빌드된 .klib 파일은 내부 구조가 다른데, 그래서 서로 다른 컴파일러에서 나온 .klib을 합치려고 하면 링커나 빌드 도중에 심볼을 못 찾거나 메타데이터가 달라서 충돌이 난다.

이게 실제로 undefined symbol, duplicate symbol, linker failed 같은 에러로 나타난다. 위에서 ios빌드과정중에 발생한 linker failed가 그 대표적인 예시다. 어떻게 보면 라이브러리 문제가 맞지만 그거뿐의 문제는 아니라는 뜻이다.

kotlin.native.cacheKind=none을 gradle.properties에 넣으면, Kotlin/Native가 라이브러리 캐시를 안 쓴다. 원래는 의존성마다 미리 빌드된 바이너리(.klib 캐시)를 저장해서 컴파일 속도를 높이는데 K2, K1 캐시가 섞이면 위에서 말한 심볼/메타데이터 충돌이 훨씬 잘나게 되는 것이다.

그래서 캐시를 꺼서 임시방편으로 이 문제를 막는 건데, 컴파일 속도가 심각하게 느려지는 부작용이 있다.