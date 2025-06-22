---
categories: [Android]
tags: [android, kotlin, compose]
mermaid: true
---

# animate*AsState

animate*AsState는 Compose에서 값의 변화를 부드럽게 애니메이션으로 연결해주는 State 기반 API다. State가 바뀌면, 정해진 애니메이션 곡선/속도에 따라 중간 값을 계속 발행하면서 UI가 즉시 값이 바뀌지 않고 목표 값으로 부드럽게 변한다.

대표적으로 animateFloatAsState, animateDpAsState, animateColorAsState, animateIntAsState 등이 있다.

```kotlin
@Composable
fun animateColorAsState(
    targetValue: Color,
    animationSpec: AnimationSpec<Color> = spring(),
    finishedListener: ((Color) -> Unit)? = null,
    label: String = "ColorAnimation"
): State<Color>
```

내가 자주 써본 건 이거다.
targetValue의 변화에 따라서 컬러 값을 다르게 줄 수 있는데, 실제로 사용한 코드는 아래와 같다.

```kotlin
val borderColor by animateColorAsState(
    targetValue = if (error) BorderError else BorderIdle
)
```

그냥 조건 값에 따라 다른 색상을 넣어준다. animationSpec에는 tween, spring, keyframes, repeatable과 같은 애니메이션의 진행 방식을 넣을 수 있는데 fade 효과도 줄 수 있고, 다양한 애니메이션을 넣을 수  있게 된다. finishedListener는 애니메이션이 끝났을 때 콜백을 설정하는 것으로 보여줘야할 애니메이션이 있으면 그걸 다 보게 한 다음 다른 행동을 하도록 수행할 때 사용하면 되는 것 같다.

대표적으로 아래 함수들이 있다.

```kotlin
@Composable
fun animateFloatAsState(
    targetValue: Float,
    animationSpec: AnimationSpec<Float> = spring(),
    finishedListener: ((Float) -> Unit)? = null,
    label: String = "FloatAnimation"
): State<Float>

@Composable
fun animateDpAsState(
    targetValue: Dp,
    animationSpec: AnimationSpec<Dp> = spring(),
    finishedListener: ((Dp) -> Unit)? = null,
    label: String = "DpAnimation"
): State<Dp>

@Composable
fun animateIntAsState(
    targetValue: Int,
    animationSpec: AnimationSpec<Int> = spring(),
    finishedListener: ((Int) -> Unit)? = null,
    label: String = "IntAnimation"
): State<Int>

@Composable
fun animateColorAsState(
    targetValue: Color,
    animationSpec: AnimationSpec<Color> = spring(),
    finishedListener: ((Color) -> Unit)? = null,
    label: String = "ColorAnimation"
): State<Color>
```

내부적으로는 State 기반의 animate API이며 값이 바뀔 때마다 Animatable 인스턴스를 이용해서 현재 값에서 목표 값으로 애니메이션을 수행한다. 중간값을 계속 recomposition 때 UI에 전달하며 각 animationSpec에 따라 시간당 값이 달라지고, 애니메이션이 완전히 끝나면 위에 기술한 finishedListener 콜백이 호출되는 구조다.

## animation도 State다

State기반이라는 부분을 조금만 더 살펴보자.

Compose에서 State란 UI를 그릴 때 기준이 되는 값이다. 예를 들어 `val a = remember { mutableStateOf(1) }`라고 해보자. `a.value`가 변하면 자동으로 recomposition이 일어나고, UI에 변화가 반영될 것이다. 즉, State가 바뀌면 UI도 알아서 다시 그린다는 게 Compose의 기본이다.

애니메이션의 모든 중간값들은 recomposition을 일으키기 때문에, state로 관리해야 UI가 자동으로 갱신되는 것이다. 시작값, 중간값, 최종값 모두 State의 값으로 관리되고 내부적으로 Animatable 객체를 쓰며 LaunchedEffect로 애니메이션을 돌린다.

근데 이래서 단점도 있는데, state에 동일값이 들어오면 UI를 새로 그리지 않는다. smart composition때문인데, 애니메이션에도 이게 적용되기 때문에 동일 targetValue가 들어가면 애니메이션이 안 돈다.

### animationSpec 종류

animationSpec은 AnimationSpec<T> 타입의 객체다. 시간 t가 흘렀을 때, 값이 어떻게 변화할지를 수식이나 알고리즘으로 결정한다. 수식이라고 해서 쫄 필요없다. 이미 각 타입별(선형, 물리 기반, 단계별, 반복 등)로 다양한 하위 구현체가 있다.

위에서 언급한 것들 정도만 설명하겠다.

tween은 시간 기반 보간 애니메이션이다. 시작값에서 목표값으로 지정한 시간 동안 linear하게(혹은 커브로) 값이 변화한다. 그래서 일반적인 fade, move, resize 등 대부분의 일정 시간 동안 변화에 사용하면 된다.

```kotlin
animationSpec = tween(
    durationMillis = 500,
    delayMillis = 100,
    easing = FastOutSlowInEasing
)
```

이 코드는 duration 동안, 현재 시간에 따라 시작값과 목표값을 easing 곡선으로 보간한다. delayMillis는 시작 전 대기 시간을 의미한다.
easing곡선에는 미리 정의된 객체를 쓰면되는데, 아까 그래서 쫄지 말라고 한 것이다.

spring은 실제 물리 기반 애니메이션이다. animate*AsState에 animationSpec의 기본 구현체로 스프링처럼 목표값에 도달할 때까지 흔들리거나 튕기듯 자연스럽게 감쇠하면서 움직인다. 자연스러운 댐핑 효과, 튕김, 반동 등을 만들 때 사용하면 되는데, 이번에 직접 사용해보니 너무 튕기는 느낌이 있어서 조절이 필요하다.

```kotlin
animationSpec = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow
)
```

dampingRatio은 감쇠 정도를 의미하는데 1에 가까울수록 바로 멈추고 0에 가까울 수록 많이 튕긴다. 근데 그냥 이것도 정의된 상수들을 쓰면 더 직관적이긴하다.. Spring.DampingRatioNoBouncy, MediumBouncy, HighBouncy, LowBouncy 같은 걸 쓰면 된다.

stiffness는 스프링 강도인데 높을수록 빠르게 멈춤, 낮을수록 느리게 진동한다. 써보면서 느낀건, scrollToUpdate같은 기능을 구현할 때 쓰면 딱 좋겠다는 점이었다.

keyframes은 좀 어렵다. 시간별로 단계적 값울 지정 가능하는 건데 특정 시점에 특정 값이 필요한 복잡한 애니메이션에 사용한다고 한다. 아직까지 써본적은 없다. 여러 개의 구간(프레임) 별로 값을 미리 지정해서 쓴다.

```kotlin
animationSpec = keyframes {
    durationMillis = 1000
    0f at 0       // 0ms에 0
    1f at 500     // 500ms에 1
    0.5f at 800   // 800ms에 0.5
    1f at 1000    // 1000ms에 1
}
```

durationMillis은 tween 때와 같이 애니메이션 전체 시간이고, 값 to 시간(ms)로 세팅할 수 있다. 각 keyframe 사이 값은 tween 방식으로 보간되어있다.

repeatable은 반복되는 애니메이션을 사용하고 싶을 때 사용한다. 근데 반복 횟수가 있기 때문에 유한한 반복이다. 무한 반복을 원하면 infiniteRepeatable을 쓰면된다.

```kotlin
animationSpec = repeatable(
    iterations = 3,
    animation = tween(300),
    repeatMode = RepeatMode.Reverse
)

animationSpec = infiniteRepeatable(
    animation = tween(1000, easing = LinearEasing),
    repeatMode = RepeatMode.Restart
)
```

내부에 animation을 지정할 수 있어서 위에 소개한 tween/spring 등과 조합도 할 수 있다. iterations은 반복 횟수, repeatMode는 반복 형태를 지정할 수 있다. RepeatMode.Restart는 처음부터 반복하는 것이고, Reverse는 왕복형태인데, 애니메이션이 처음과 끝이 이어지는 형태가 아니라면 Reverse를 사용하는 게 자연스럽다.

나는 파도치는 모양을 이걸로 구현했다.