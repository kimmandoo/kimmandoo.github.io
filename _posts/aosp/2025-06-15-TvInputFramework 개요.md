---
categories: [AOSP]
tags: [aosp, androidtv]
mermaid: true
---

## TvInputFramework(TIF)

![img](assets/img/post/0615/image.png){: width="500" height="500" }

Android TV 시스템은 크게 안드로이드 프레임워크가 제공하는 구성 요소(녹색)와 디바이스 제조업체가 구현해야 하는 하드웨어 및 소프트웨어 구성 요소(파란색)로 나뉜다.

TvApp은 사용자와 직접 상호작용하며, TV InputManager를 통해 TVInput의 상태를 관리하고 TV Provider를 통해 콘텐츠 정보를 얻는다.

TvApp과 TV Provider는 직접 연결되지 않는다. 대신 TvInput이 채널 및 프로그램 정보(정적 메타데이터)를 TV Provider로부터 받아와 직접 콘텐츠(스트림)를 처리하여 화면에 출력한다. 예를 들어 넷플릭스와 같은 타사 TvInput이나 HDMI와 같은 패스 스루(Passthrough) 입력은 자체 콘텐츠나 외부 입력 소스만 표시할 수 있으며, 내장 튜너나 IPTvInput과 같은 시스템 내부의 입력 소스는 플랫폼 키로 서명된 시스템 앱만 접근하여 콘텐츠를 표시할 수 있다. 이는 패키지 접근이 제한된 형태(locked-access)이기 때문이다. MediaProjection을 사용하여 Virtual Display 캡처를 진행해도, 이러한 입력은 보안 제한으로 인해 검은 화면으로 표시된다.

하드웨어 TvInput 앱이 가진 `TV_INPUT_HARDWARE` 권한은 TvInput 관리자 서비스(TvInputManagerService)에 신호를 보내, 부팅 과정에서 TvInput 서비스를 호출하고 관련된 입력을 시스템에 추가하도록 한다.

![img](assets/img/post/0615/image (5).png){: width="500" height="500" }
위에서 내장/IPTV튜너가 패스 스루라고 했는데, 패스 스루 TvInput은 신호를 가공없이 그대로 통과시키는 것으로 채널과 프로그램을 저장하지 않는다. TvInputService를 통해 구현되며, 다른 tvInput과 마찬가지로 TvInputManager에 의해 관리된다.

## TVInputManager(TVIM)

TVInputManager(TVIM)는 Android TV Input Framework의 핵심 구성 요소로, TvApp과 TvInput 간의 상호작용을 제어하고 관리하는 추상화 계층이다. TVIM은 모든 TvInput의 목록을 관리하며, 각 입력에 대한 개별 세션을 생성하고 관리한다.

각 TvInput마다 독립적인 세션을 생성하여 콘텐츠 스트림의 시작, 정지, 상태 변화 등을 TvApp에서 제어할 수 있도록 지원하고, 시스템에 등록된 모든 TvInput의 상태를 관리하며, TVApp이 이를 확인하고 접근할 수 있도록 목록을 제공한다.

TVIM에서 이벤트 리스너를 설정하고 관리하면, TvInput에서 스트림의 신호 강도 변화, input 활성화/비활성화 상태 변화같은 상태변화를 볼 수 있게 되는데, 오디오 볼륨 조절도 여기에 속한다.

## TvProvider

![img](assets/img/post/0615/image (2).png){: width="500" height="500" }

TvProviderDB는 sqlite로 구현되어있고 채널과 프로그램정보를 TvInput 에서 받아다가 저장하는 역할을 하는데, TVInput 및 TV App만 TvProviderDB에 온전히 액세스하고 KeyEvents를 수신할 수 있다. 이 말은 플랫폼 키를 가진채로 signing된 시스템앱이어야한다는 말과 동일하다.

![img](assets/img/post/0615/image (3).png){: width="500" height="500" }

TV Provider는 내부적으로 '방송 장르'를 '표준 장르'로 매핑한다. TvInput은 기본 방송 표준의 값으로 '방송 장르'를 채우는 역할을 하며, '표준 장르' 필드는 `android.provider.TvContract.Genres`에서 연결된 올바른 장르로 자동으로 채워진다.

즉 좀 더 정리하자면, TV Provider는 TvContract를 통해 채널과 프로그램 데이터를 저장하고 관리하는 역할을 한다. 위에서 예시로 사용한 내장 튜너(Built-in-Tuner TV Input)나 IPTV input은 채널 목록, EPG, 장르와 같은 데이터를 TV Provider에 등록하여 TV App이 이를 쿼리하고 표시할 수 있도록 한다.

이 데이터는 정적데이터이며, TV App이 채널 탐색, 프로그램 스케줄 확인 등에 사용할 수 있도록 테이블로 맵핑되어있다.

![img](assets/img/post/0615/image (4).png){: width="500" height="500" }

각 방송 표준(broadcast system type)은 장르를 정의하는 방식이 다를 수 있기 때문에 한번 추상화시켜서 통일화된 장르를 채워주는 것이다

## TV Provider의 데이터 저장 방식

![img](assets/img/post/0615/image (6).png){: width="300"}

TV Provider는 채널 및 프로그램 정보를 저장하는 데 사용되는 표준 필드 외에도, TV Input이 자체적인 임의 데이터를 저장할 수 있도록 각 테이블 `TvContract.Channels와 TvContract.Programs` 에 BLOB(Binary Large Object) 필드를 제공한다. BLOB은 바이너리 형태로 문자열, 숫자, 객체와 같은 복잡한 데이터 구조를 직렬화(serialize)하여 저장하는 데 사용되는 데이터 타입인데, 이 필드가 바로 `COLUMN_INTERNAL_PROVIDER_DATA`이다.

COLUMN_INTERNAL_PROVIDER_DATA는 Android TV의 TvContract API에 정의된 필드이다. 주로 연결된 튜너의 주파수와 같은 TV Input이 필요로 하는 맞춤(custom) 데이터를 저장하는 용도로 사용되며, 데이터는 프로토콜 버퍼(Protocol Buffer)나 다른 직렬화 형태로 제공될 수 있다.

TV Provider의 DB 테이블은 크게 디스플레이, 메타데이터, 내부데이터, 플래그 이렇게 4가지 필드 그룹으로 구성된다.

- Display: 채널 또는 프로그램의 기본 식별 정보 및 사용자에게 보여질 이름, 번호 등 간략한 표시 정보
- Metadata: 채널 및 프로그램과 관련된 추가 설명, 시간 정보, 장르 등의 부가적 정보. 주로 사용자가 EPG(편성표) 등을 통해 콘텐츠를 탐색할 때 사용된다
- Internal Data: TV Input이 커스텀 데이터를 저장할 수 있는 중요한 필드로, COLUMN_INTERNAL_PROVIDER_DATA와 같은 BLOB 필드를 통해 접근할 수 있다
- Flag: 채널 탐색이나 콘텐츠 제한 기능을 관리하기 위한 데이터이다. 예를 들어 Searchable 필드를 이용하면 특정 채널이 검색이나 탐색 기능에서 제외되도록 설정할 수 있다(지역락같은 설정)

TvInput은 설명하려면 따로 분리하는 게 좋을 것 같아서, 대략적인 프레임워크 설명은 여기서 마치겠다.