---
categories: [Books]
tags: [kotlin, kotlin in action]
---

<img title="" src="https://freecontent.manning.com/wp-content/uploads/Jemerov-Kotlin-HI.jpg" alt="" data-align="center" width="204">

## 개요

### 함수 호출과 정의

```kotlin
fun <T> joinToString(
        collection: Collection<T>,
        separator: String,
        prefix: String,
        postfix: String
): String {

    val result = StringBuilder(prefix)

    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}
```

함수에 관해 얘기하는 이번 3장에서 계속 사용될 제네릭 함수다. 구분자를 받아서 앞뒤로 "(", ")"를 붙여서 반환하는 함수다.

하나 하나 살펴보자.

코틀린에서는 인자에 이름을 붙일 수 있다. 함수를 호출할 때 `separator = "."` 와 같은 형식으로 인자를 넣어 전달할 수 있는데, 중간에만 넣을 수는 없고, 명명한 인자 이후로는 모두 이름을 붙여 호출해야된다는 규칙이 있다. 이렇게 사용하면 인자를 확실히 구분할 수 있어 여러개의 인자를 갖는 함수에 대한 혼동을 줄일 수 있다.

인자에 이름을 붙이는 행동은 `디폴트 파라미터`와 연결되는데, 함수 선언시 파라미터에 값을 지정해두면 해당 인자에 값이 들어오지 않을 경우 미리 지정해둔 값으로 파라미터 입력값을 대신한다. 이것을 디폴트 파라미터라고 하며, 디폴트 파라미터가 있는 함수를 이름붙여 사용하면 파라미터 순서와 관계없이 인자를 전달 할 수 있게 된다.

### 최상위 함수와 최상위 프로퍼티

코틀린에서는 함수를 꼭 클래스안의 메서드로 작성할 필요가 없다. 그냥 파일을 생성해서 함수를 적으면 되고, 해당 함수를 사용할 때는 함수가 들어있는 파일의 패키지만 import해주면 사용할 수 있다. 어떻게 이게 가능할까? 그 이유는 코틀린 컴파일러가 최상위함수가 들어있는 파일을 컴파일 할 때 새로운 클래스를 만들어서 JVM에 넘겨주기 때문이다.

```kotlin
package ch03.JoinToString

fun <T> joinToString(
       ...
){ ... }
```

이런식으로 정의하고, 사용할 수 있다. 코틀린 컴파일러가 생성한 새로운 클래스의 이름은 함수가 들어있던 파일과 대응하고, 최상위 함수는 정적 함수로 존재하게 된다. 이런 모양은 함수뿐만 아니라 프로퍼티도 가능한데, 클래스 범위 밖에서 `const val`로 상수를 선언하는게 그 예시다.  const val은 자바의 `public static final`로 컴파일된다. 단 const 는 primitive타입들과, String 타입만 가능하다.

### 확장함수와 확장 프로퍼티

#### 확장함수

확장함수는 기존 API를 건드리지 않고 클래스 밖에서 해당 클래스의 멤버 메서드인 것처럼 사용할 수 있는 함수다.  예시를 보자.

```kotlin
fun String.lastChar(): Char = this.get(this.length - 1)
```

String클래스를 확장해서 만든 lastChar()함수다. String클래스 객체에서 사용할 수 있게된다. 이때 확장할 클래스 이름(여기서는 String)을 `수신 객체 타입(receiver type)`이라고 하고 this.get()에서 사용되는 this가 호출되는 대상 값으로 `수신 객체(receiver object)`라고 부른다. 즉 수신객체는 클래스의 인스턴스 객체라고도 할 수 있다. 확장함수는 내부적으로 수신객체를 첫번째 인자로 받기 때문에 실행시점의 부가비용이 들지 않는다.

위 코드에서 사용된 this는 가독성을 위해 사용된 것이지 함수 본문에서 생략가능하다. 이렇게 클래스 밖에서 정의된 확장함수는 캡슐화를 깰까? 정답은 "깨지않는다"이다. 확장함수는 클래스의 private, protected 멤버에 접근할 수 없기때문에 캡슐화를 지키고 있다고 할 수 있다. 이렇게 만든 확장함수는 import해서 사용할 수 있다.

```kotlin
import string.lastChar
import string.*
```

아래 위 모두 확장함수를 사용할 수 있게 해주는 import구문이다. 확장함수와 다른 패키지의 함수가 이름이 겹치면 as를 이용해 다른 이름으로 확장함수를 별명지어 사용할 수도 있다.

마냥 좋을 것 같은 확장함수도 못하는게 있는데, 오버라이딩이다. 안드로이드에서 setOnClickListener의 object로 View의 onClick이 오버라이딩되는데, 이러면 오버라이딩 한 함수가 실행되는 것이 일반적이다. 그러나 이건 클래스 안에 들어있는 것이라 가능한 것이고, 확장함수는 클래스 밖에 있어서 오버라이딩 할 수 없다. 오버라이딩이 동적으로 결정되는 것이 반해 확장함수는 수신객체, 수신객체 타입에 의해 정적으로 결정되기 때문이다. 

```kotlin
val view: View = Button()
    view.showOff()
```

view변수는 View타입이기 때문에 View의 확장함수 showOff를 실행하지 Button의 showOff를 실행하지 않는다. view: Button 이었다면 Button의 showOff를 실행했을 것이다.

#### 확장 프로퍼티

확장 프로퍼티는 함수형태가 아니라 프로퍼티 형태로 정의하는 함수라고 보는 게 편하다. 인스턴스의 상태를 저장할 수없고 단지 프로퍼티 문법만 이용하기 때문이다. 확장함수를 프로퍼티 문법으로 바꾸려면 get()을 이용해야한다. 값을 저장할 수 없어 backing field가 없기 때문에 get()으로 게터를 정의해줘야하며 경우에 따라서 set()을 이용해 간단한 조작도 가능하긴하다. 아래가 그 예시다.

```kotlin
var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }
```

### Collection

코틀린의 컬렉션은 자바 컬렉션을 그대로 사용한다. 거기에 확장함수로 코틀린에서 편하게 사용할 수 있는 API가 추가된 것이다.

#### varargs

이건 익숙한 표현이다. 가변길이 인자인데, 원하는 만큼 값을 넣어 인자로 넘기면 컴파일러가 배열에 값을 넣어 처리하는 것이다. 배열을 가변인자로 넘길때는 코틀린과 자바가 다른데, 코틀린에서는 전달할 배열 앞에 스프레드 연산자인 *를 붙이면 원소들을 펼쳐서 인자로 넣어준다.

```kotlin
val list = listOf("args: ", *args)
```

#### 중위호출과 구조 분해

mapOf(a to b)에서 a는 key, b는 value로 맵핑되어 저장된다. 이때 사용되는 to는 키워드가 아니라 일반 메서드(확장함수다)이고, 중위호출(infix)로 사용된 것이다. `수신객체 to 유일한 메서드 인자`로 되는데 to함수의 반환이 Pair임을 알면 구조분해까지 이해할 수 있다. Pair는 자바에는 없는 개념인데 순서쌍을 나타낸다.

```kotlin
val (num, name) = 1 to "one"
```

으로하면 num에 1, name에 one이 들어가는데, 이걸 구조분해선언, destructuring declaration이라고 한다. 우리는 이미 구조분해선언을 사용했다. for문에서 사용한 `.withIndex()`가 그 정체다.

to가 map에서 사용돼서 map에서만 사용해야되는 줄 알 수도 있는데, 순서쌍이 필요하면 to를 사용할 수 있다.

### 문자열과 정규식

정규식은 꼭 한번 익히고 넘어가야했던 부분이다. 자바의 문자열 메서드 중 split()의 인자로 "."을 사용할 수 없는데, 이건 split의 인자가 정규식이고 `.`이 모든 문자를 나타내는 정규식으로 해석되기 때문이다.

```java
import java.util.Arrays;

class Main {
  public static void main(String[] args) {
      String test = "12.51.6262.6515.";
      System.out.println(Arrays.toString(test.split(".")));
  }
} 
```

빈 문자열을 반환하는 걸 볼 수 있다. 코틀린에서는 이런 문제를 피하기 위해 기본을 String으로 받고 .toRegex()로 정규식임을 명시해주는 방법을 사용했다. 이렇게 쓰면 정규식을 표현할 때 \ 역슬래시를 사용하기 위해 \\\로 표현하던걸 """ """를 이용해 이스케이프 할 노동을 줄일 수 있게된다. 추가로 삼중 따옴표에도 문자열 템플릿을 넣을 수 있다.

정규식 문법은 필요할 때마다 찾아서 사용하면 된다.

### 로컬 함수

코드 중복을 줄이는 방법 중에 하나인데, 함수에서 추출한 함수를 원함수 내부에 중첩시키는 방법이다. 그냥 함수 안에 또 함수를 선언하는 것이다.

```kotlin
fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
               "Can't save user $id: empty $fieldName")
        }
    }

    validate(name, "Name")
    validate(address, "Address")
}
```

validate가 로컬함수다. 함수 스코프를 보면  적어도 validateBeforeSave함수, 즉 validate함수 바깥에 있는 파라미터, 변수에 접근할 수 있게된다. 함수 중첩은 1 depth까지만 하는게 권장사항이다.







---

> 참조:
> 
> 코틀린 인 액션 3장