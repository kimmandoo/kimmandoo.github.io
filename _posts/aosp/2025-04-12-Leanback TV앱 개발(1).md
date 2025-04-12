---
categories: [AOSP]
tags: [aosp, leanback]
mermaid: true
---

TV 앱 개발에 입문하게됐다. 

TV 앱 개발을 그냥 일반 앱 개발할 때 처럼 해도 보여지는 것엔 큰 문제가 없지만, TV는 터치스크린이 없고, 리모컨을 통한 제어가 주된 환경이라 신경 쓸 게 조금 많아진다. 리모컨의 방향키와 선택 버튼(DPAD)이 주 입력 수단이다. 음성 검색이나 게임 컨트롤러를 지원하지만, 입력은 제한적이며 배터리 제약이 없지만 기기 자체의 성능이 높지 않기 때문에 최적화는 필수다.

모바일 개발과, TV개발의 차이는 아래와 같다.

|  | 안드로이드 TV 개발 | 안드로이드 모바일 개발 |
| --- | --- | --- |
| UI 설계 | 큰 화면, 거리에서 보기에 최적화, Leanback 라이브러리 사용 | 작은 화면, 터치 기반, 표준 레이아웃 사용 |
| 입력 처리 | 리모콘 키 이벤트 (DPAD, 선택 버튼) | 터치 이벤트 (탭, 스와이프) |
| 내비게이션 | 포커스 기반, 선형/그리드 탐색 | 터치 기반, 자유로운 내비게이션 |
| 하드웨어 고려사항 | 터치 스크린 없음, GPS/카메라와 같은 기능 제한 가능 | 다양한 하드웨어 기능 지원 (터치, GPS, 카메라 등) |
| 자원 관리 | 더 큰 화면에 맞춘 이미지/텍스트 최적화 | 작은 화면과 **배터리 효율성** 고려 |

지금은 Compose로 TV앱을 개발하는 게 권장사항이지만 내가 유지보수 해야하는 앱은 Leanback 툴킷을 사용하고 있기 때문에, 튜토리얼을 천천히 진행해보기로 했다.

> [android-tv-application-hands-on-tutorial](https://corochann.com/android-tv-application-hands-on-tutorial/)

위 링크에서 `@corochann`님이 만들어두신 튜토리얼을 Kotlin으로 진행한다.

## 초기 설정
진짜 처음부터 하는 거라서, TV탭 -> No Acitivty로 시작한다. No Acitivty로 만들면 진짜 아무것도 없다.

의존성 추가해주고

```toml
[versions]
leanback = "1.0.0"


[libraries]
androidx-leanback = { group = "androidx.leanback", name = "leanback", version.ref = "leanback" }

# dependency
implementation(libs.androidx.leanback)
```

Theme도 설정해준다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.TV" parent="@style/Theme.Leanback" />
</resources>
```
Theme을 설정해주지 않으면, Leanback 컴포넌트를 빌드할 때, xml단에서 오류가 발생한다. Leanback의 Theme을 기본 테마로 넣고, 이걸 manifest에서 지정해줘야 오류가 나지않는다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-feature
        android:name="android.hardware.touchscreen"
        android:required="false" />
    <uses-feature
        android:name="android.software.leanback"
        android:required="true" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/Theme.TV">
        <activity
            android:name=".MainActivity"
            android:banner="@drawable/banner"
            android:exported="true"
            android:theme="@style/Theme.TV"
            android:icon="@drawable/banner"
            android:logo="@drawable/banner">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
                <category android:name="android.intent.category.LEANBACK_LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

매니페스트를 작성해준다. 물론 그 전에 MainAcitivity를 만들어두고, 레이아웃 파일까지는 만들어놓자.

```xml
    <uses-feature
        android:name="android.software.leanback"
        android:required="true" />
    <uses-feature
        android:name="android.hardware.touchscreen"
        android:required="false" />
```
이 두 옵션의 의미가 있다. 

앱이 Leanback 라이브러리(안드로이드 TV용 UI 프레임워크)를 지원하는 장치를 대상으로 한다는 의미가 첫번째 것이다. `android.software.leanback`은 안드로이드 TV 환경을 말한다.
두번째는 터치스크린을 지원하는 기기인지 설정하는 것이다.

![Image](https://github.com/user-attachments/assets/6fe7b95a-1ed3-45e5-88a6-c2e10ebb1c8e)

배너또한 중요하다. 배너의 역할은 앱을 홈화면에서 노출시켜줄 수 있는 것 인데, 앱 로고라고 생각하면 편하다. 위 사진과 같이 노출되려면 배너를 꼭 넣어줘야한다.

![img](https://file.notion.so/f/f/f50a2473-33af-4512-be58-ea90d754edbc/8139045e-69de-49cd-ac2b-b80f5f650c73/image.png?table=block&id=1d2d41b8-11d4-803a-8ce8-cc0177275d28&spaceId=f50a2473-33af-4512-be58-ea90d754edbc&expirationTimestamp=1744502400000&signature=q6NXQUBm2n_FtG-I6a5zzeuZE9OEw75Ju6SlwXqMakg&downloadName=image.png)

이제 화면을 구성해보자.

```kotlin
import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment

class MainFragment: BrowseSupportFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
```

MainActivity에 넣을 MainFragment로 BrowseSupportFragment를 사용해주면 된다. MainFragment는 그냥 간단하게, FragmentContaierView에 name으로 지정해주면 끝난다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.fragment.app.FragmentContainerView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:name="com.kimmandoo.tv.MainFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

</androidx.fragment.app.FragmentContainerView>
```
이대로 실행시키면 잘 동작할까?

터진다. MainActivity를 템플릿으로 생성하면 `AppCompatActivity`로 생성되는데, 이건 Theme을 AppCompat으로 해줘야 올바르게 동작한다. 지금 우리는 TV앱을 빌드하기 위해 Leanback을 사용하기로 했기 때문에, Theme이 어긋나서 올바른 리소스를 찾지 못하는 것이다.

```kotlin
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

그래서 `FragmentActivity`로 만들어야한다. tv에서 실행할꺼니까 edgeToEdge 날리고, view padding 세팅 다 날리고 FragmentActivity로 바꿔주면 된다.

BrowseSupportFragment는 아래 사진과 같은 구성으로 되어있다.

![dd](https://file.notion.so/f/f/f50a2473-33af-4512-be58-ea90d754edbc/461366bb-de12-46b7-a2e8-f8ba00bfdbba/image.png?table=block&id=1d2d41b8-11d4-8001-901d-ef24c7e142cb&spaceId=f50a2473-33af-4512-be58-ea90d754edbc&expirationTimestamp=1744502400000&signature=0jJtevvOiKM1xrCUoVGhPyBgjINphbFCTOUgYdJmA0c&downloadName=image.png)

BrowseSupportFragment는 Headers와 Rows로 나뉘어져있다. Headers에서는 Drawer처럼 메뉴가 있고, Rows는 Detail화면이라고 보면 될 것 같다.

튜토리얼을 그대로 따라가기 위해 테마 색상도 지정해보자.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="fastlane_background">#0096e6</color>
    <color name="search_opaque">#ffaa3f</color>
</resources>
```

fastlane이라는 단어의 의미가 있다. Leanback 라이브러리에서 Fastlane은 홈 화면의 왼쪽에 위치한 수직 탐색 메뉴, 카테고리 선택 UI를 의미한다. 사용자가 리모컨으로 빠르게 탐색할 수 있는 영역으로 보통 TV 앱에서 앱의 메인 메뉴 또는 사이드바다.

색상을 적용하기 위해 MainFragment를 수정해보자.

```kotlin
class MainFragment: BrowseSupportFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUIElements()
    }

    private fun setUpUIElements(){
        title = "Hello Android TV"
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            brandColor = resources.getColor(R.color.fastlane_background, null)
            searchAffordanceColor = resources.getColor(R.color.search_opaque, null)
        }
    }
}
```

![dd](https://file.notion.so/f/f/f50a2473-33af-4512-be58-ea90d754edbc/79a2ca8b-8fd3-4801-97be-b6532bccc8f6/image.png?table=block&id=1d2d41b8-11d4-807a-8d23-fec730162055&spaceId=f50a2473-33af-4512-be58-ea90d754edbc&expirationTimestamp=1744502400000&signature=sOByk5ZbKTBMqpEADb-An8VoB-lXmj37AlvxjuCuwJk&downloadName=image.png)


brandColor로 색상을 넣어주면, 사이드바에 색이 들어간다. 

```kotlin
private fun setUpUIElements(){
    title = "Hello Android TV"
    badgeDrawable = ResourcesCompat.getDrawable(resources, R.drawable.banner, null)
    headersState = HEADERS_ENABLED
    isHeadersTransitionOnBackEnabled = true
    brandColor = resources.getColor(R.color.fastlane_background, null)
    searchAffordanceColor = resources.getColor(R.color.search_opaque, null)
}
```
title 대신에 badge로 이미지를 넣을 수 있는데, title, badge 둘 다 넣으면 badge만 보인다.