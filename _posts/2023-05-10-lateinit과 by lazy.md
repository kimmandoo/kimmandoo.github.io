---
categories: [android]
tags: [android, kotlin]

---

# lateinit과 by lazy의 차이

사용하면서도 대략적으로 밖에 알지 못했던 두 기능을 정리해보자.

## lateinit

선언하는 시점과 초기화되는 시점을 분리하는 방법이다. 선언을 먼저 해두고 초기화를 나중에 하므로 사용전에 반드시 초기화를 하고 사용해야 된다. 

```kotlin
private lateinit var value: MyDataClass
```

이렇게 onCreate전에 선언해두고, 사용할 때 초기화를 하고 사용하면된다.

초기화를 안한상태에서 lateinit 선언된 변수를 사용할 경우 `UninitializedPropertyAccessException`이 발생한다. 조금 더 safe하게 사용하려면 `isInitialized()` 메서드를 이용해 예외처리를 할 수 있다.

```kotlin
if(this::value.isInitialized) {
    // 바로 쓰면됨
} else {
    // 초기화를 하던가, 다른 동
}
```

## by lazy

Lazy한 초기화는 디자인패턴의 일종이다. 객체에 처음 접근할 때 초기화를 할 수 있고, 그게 아니면 초기화 할 필요가 없다. 이건 앱이 켜질 때 (onCreate) 객체가 무조건 초기화 되는 게 아니라, 실제 사용시 초기화가 이루어지기 때문에 사용 효율을 늘려주는 효과가 있다.

```kotlin
private val value: MyDataClass by lazy {
        print("Lazy initialization")
        MyDataClass("Mingyu", "Kim")
    }
```

이렇게 선언하면 처음 사용 할 때 lazy 블럭 안에 있는 코드가 실행된다. 처음 접근할 때 초기화와 동시에 인스턴스 생성이 되는데, 이러면 그 생성 시점 이후에는 인스턴스 생성이 발생하지않고(따로 초기화 되지않고) 초기화된 값을 계속 사용한다. 작동 방식이 싱글턴과 유사해 실제로 싱글턴을 구현할 때 by lazy를 사용한다.

## 뭐가 다를까?

대충 넘어가면 둘이 비슷하게 느낄 수 있다. 

`lateinit`은 var를 사용하고 `by lazy`는 val을 사용한다. 즉 lateinit으로 선언된 mutable한 var값은 런타임 중 계속 값이 변할 수 있고(`non-nullable`하다.), lazy로 선언된건 처음 초기화 값이 그대로 유지된다(사용하지않으면 초기화되지도 않아서 nullable해도 괜찮다.). 그래서 lateinit은 타입이 고정되는 원시타입들을 사용할 수 없고, lazy는 상관없다.

이걸 쓰레드 관점에서 보면 lateinit은 안전하다고 할 수 없고 lazy는 안전하다고 말 할 수 있게된다. lateinit은 초기화가 안돼도 접근가능한 반면, lazy는 접근하면 초기화되므로 이게 근거가 될 수 있다.

---

> 참조:
> 
> https://medium.com/huawei-developers/kotlin-lateinit-vs-by-lazy-initialization-example-tutorial-c19d84216480https://medium.com/huawei-developers/kotlin-lateinit-vs-by-lazy-initialization-example-tutorial-c19d84216480