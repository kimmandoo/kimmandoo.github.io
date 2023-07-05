---
categories: [Android]
tags: [android, error]
---

텍스트뷰의 `textColor`를 활성화여부에 따라 바꿔줘야했는데, 그냥 selector로 만들어서 하니 아래와 같은 예상하지 못한 색상으로 나왔다.

![img](https://user-images.githubusercontent.com/46841652/251077220-4d1eccdf-4ff2-487b-878d-3651fb981604.png)

처음에는 테마가 문제인가 싶어 theme.xml파일을 조작해봤는데 이게 문제가 아니었다.

해결방법이 너무 간단했는데, color 디렉토리를 만들어 거기에 selector를 위치시키는 것이다. 

```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_activated="true" android:color="@color/White_01"/>
    <item android:state_activated="false" android:color="@color/Gray_03"/>
</selector>
```

코드는 건드릴게 없고 /res밑에 `color`라는 디렉토리를 만들어 주면 이 `/color`아래에 위치하는 리소스파일은 `Color Resource File`로 인식한다.



color속성이 아니라 drawable속성을 사용하면 색상으로 인식하지 못해 color 디렉토리로 옮겨도 오류가 계속 남아있게 되니 주의해야한다.

`/color` 에 있는 색상정보와 `/values/colors.xml`은 똑같이 `@color/색상`으로 접근 할 수 있다. 즉 `/color`밑의 파일이름이 리소스 ID로 사용된다.

`android:textColor="@color/selector_signup_text_color"` 



여기서 작성을 마치기에는 내용이 부실하기 때문에 selector의 state 속성을 살펴보자.

## selector의 state list

모든 상태값은 boolean으로 true/false로 구별한다.

`android:state_pressed`

객체가 눌렸을 때 `true`이며 버튼이 눌리지 않은 상태에서는 `false`다.

`android:state_focused`

객체에 포커스가 있을 때(예: 트랙볼 또는 D패드를 사용하여 버튼이 강조표시된 경우) `true`이며 포커스가 없는 상태에서 이 항목이 사용되면 `false`다. 포커스는 생각보다 볼 일이 적으므로 잘 사용되지 않는다.

`android:state_selected`

객체가 선택되었을 때 `true`이며 객체가 선택되지 않았을 때 `false`다.

`android:state_checkable`

객체가 선택 가능할 때  `true`이며 객체가 선택 가능하지 않을 때 `false`다. 객체가 선택 가능한 위젯과 선택 가능하지 않은 위젯 간에 전환이 가능한 경우에 유용하다.

`android:state_checked`

객체가 선택되었을 때 `true`이며 객체가 선택되지 않았을 때`false`다. 체크박스를 구현할 때 유용하다.

`android:state_enabled`

객체가 사용 설정되었을 때(터치/클릭 이벤트를 수신할 수 있음) `true`이며 객체가 사용 중지되었을 때  `false`다.

`android:state_activated`

객체가 영구 선택으로 활성화될 때(예: 영구 탐색 뷰에서 이전에 선택한 목록 항목을 '강조표시'하기 위해) 이 항목을 사용해야 하는 경우 `true`이고, 객체가 활성화되지 않았을 때 사용해야 하면 `false`다.

API 수준 11부터 사용가능하다. 

`android:state_window_focused`

애플리케이션 창에 포커스가 있을 때(애플리케이션이 포그라운드에 있음) `true`이며 애플리케이션 창에 포커스가 없을 때(예: 알림 창이 풀다운되거나 대화상자가 표시됨) `false`다.



내가 사용한 것은 activated인데, 영구 탐색(persistent selection) 이라는 게 뭔가 싶어 좀 찾아보니, 현재 화면에서 일회성으로 사라지지 않는 상태인 것으로 보인다.



---

> 참조:
> 
> https://developer.android.com/guide/topics/resources/color-list-resource?hl=ko