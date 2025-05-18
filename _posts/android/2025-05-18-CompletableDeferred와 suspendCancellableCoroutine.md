---
categories: [Android]
tags: [android, kotlin, 일퀘]
mermaid: true
---

# CompletableDeferred

CompletableDeferred는 비동기 콜백 작업을 코루틴 스타일로 처리할 때 자주 사용되는 클래스다. Deferred(값을 나중에 반환하는 Future 역할)에서 파생되었다.

외부에서 “완료 시점”을 직접 제어하거나, 여러 콜백에서 완료/실패 신호를 합쳐야 할 때 쓴다.

```kotlin
public interface CompletableDeferred<T> : Deferred<T> {
    fun complete(value: T): Boolean
    fun completeExceptionally(exception: Throwable): Boolean
}
```

제일 중요한 건 3개다. Deferred타입에서 나오는 await, 그리고 구현된 complete와 completeExceptionally다.

complete는 값을 넣어주면 해당 CompletableDeferred 및 그 하위 코루틴까지 결과가 전파된다. 즉, 여러 코루틴이 엮여 있더라도 모든 작업이 끝난 후에만 complete가 boolean을 반환하는 구조다.
completeExceptionally는 예외가 발생한 경우에 호출되어 외부로 에러를 전달하는 함수다.

```kotlin
suspend fun fetchDataFromServer(): Data {
    val deferred = CompletableDeferred<Data>()
    
    someAsyncOperation { data ->
        // ... 작업들
        deferred.complete(data)
    }
    
    return deferred.await()
}
```

CompletableDeferred클래스로 객체를 하나 만들고, 비동기 작업 블록안에 이 변수를 넣어주면 제어할 수 있게된다. 해당 블록의 작업이 모두 끝나면 complete가 불리고, 그 안에 담긴 값을 defered.await()로 꺼내 쓸 수 있다.

이전에 그냥 사용하던 suspendable coroutine(async(얘도 반환값이 Deferred라서 await가 가능하다), delay같은) 것 과 차이점이 바로 보인다면 코루틴 마스터라고 할 수 있겠다. 일반적으로 사용하던 async 같은건 바깥에 있던 변수로 내부 코루틴을 제어하는 것이 불가능했다. 그런데 이 코드를 보면, 비동기 작업을 할 코루틴 스코프 바깥에 객체를 선언해서 값을 감시하며 결과값도 가져온다는 차이점을 볼 수 있다.

그럼 의문점이 하나 생긴다. async빌더를 쓰면 더 간단한데 왜 굳이 CompletableDeferred를 써야하나? async 계속 써도 된다. 사용법 간단하고 await로 값 받아올 수 있다. 그런데 CompletableDeferred를 사용하면 수동으로 결과를 설정해서 가져오기 때문에 좀 더 정교하게 값을 다룰 수 있고, 작업이 취소될 때 수행할 내용을 더욱 자세하게 지정할 수 있게된다.

좀 더 예시를 들어보겠다. 여러개의 api 결과값을 조합해서 사용해야된다고 가정해보자.

```kotlin
suspend fun fetchDataFromServer(): String {
    val deferred1 = CompletableDeferred<String>()
    val deferred2 = CompletableDeferred<String>()

    viewModelScope.launch {
        val result1 = fetchData1()
        deferred1.complete(result1)
    }

    viewModelScope.launch {
        val result2 = fetchData2()
        deferred2.complete(result2)
    }

    // 두 작업이 모두 완료될 때까지 기다림
    val data1 = deferred1.await()
    val data2 = deferred2.await()

    return "$data1 and $data2"
}
```

data1을 받아올 때는 deferred1이 끝나야 값을 가져오고, data2역시 deferred2가 끝나야 값을 가져온다. 완료가 되는 시점에 어떤 값을 가져올지 명시하는 게 가능하기 때문에 가독성이 좋아지고, 객체이름을 작업이름으로 바꿔준다면 더 읽기 좋게 바꿀 수 있게된다.

```kotlin
val deferred1 = viewModelScope.async { fetchData1() }
val deferred2 = viewModelScope.async { fetchData2() }
```

# suspendCancellableCoroutine

앞서 얘기한 CompletableDeferred와 거의 동일한 개념이다. 차이점으로는 suspendCancellableCoroutine이 코루틴 빌더라는 것이다.

```kotlin
suspend fun <T> suspendCancellableCoroutine(
    block: (CancellableContinuation<T>) -> Unit
): T
```

코루틴 본문을 CancellableContinuation으로 받기 때문에 이 스코프 안에서 작업을 수행할 때 취소에 대한 행동을 정의할 수 있다. 작업이 완료되었다면 resume, 작업중 예외가 발생했다면 resumeWithException으로 제어가능하다. 값을 반환하기 때문에 이것 역시 콜백기반의 비동기작업에 적합하다.

```kotlin
suspend fun fetchData(): String {
    return suspendCancellableCoroutine { continuation ->
        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                continuation.resume("User data")
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }

        continuation.invokeOnCancellation {
            job.cancel()
        }
    }
}
```

job으로 지정한 작업이 완료되면, continuation의 resume에 값을 전달해서 fetchData의 반환값으로 사용하고, 작업중에 예외가 발생했다면 resumeWithException을 사용해 작업중이던 코루틴 외부로 전파시킬 수 있다. 그리고 invokeOnCancellaion 블록에는 취소가 발생할 때 수행할 작업을 정의할 수 있다.

resumeWithException은 예외를 전파시키기 때문에 예시코드 기준으로 fetchData를 호출하는 쪽에서 예외처리를 해줘야하고, invokeOnCancellation은 외부에서 suspendCancellableCoroutine에 대한 취소가 발생했을 때 이전 입력된 값을 날린다던가 하는 초기화 로직을 넣어두면 될 것 같다.

이때 Cancellable의 의미가 중요하다. 이름에 들어있는 Cancellable은 이 코루틴 스코프를 외부에서 취소시킬 수 있는 지에 대한 여부를 의미한다. 즉 외부에서 예외를 받고 -> 취소를 호출하면 invokeOnCancellation 내부에 작성해둔 코드가 실행된다.

try-catch 대신 runCatching을 사용해 좀 더 코틀린스럽게 바꿔보면 아래와 같이 할 수 있을 것 같다.

```kotlin
suspend fun fetchData(): String {
    return suspendCancellableCoroutine { continuation ->
        val job = CoroutineScope(Dispatchers.IO).launch {
            val result = runCatching {
                "User data"
            }

            result.onSuccess { data ->
                continuation.resume(data)
            }

            result.onFailure { e -> // Throwable이 들어온다.
                continuation.resumeWithException(e)
            }
        }

        continuation.invokeOnCancellation {
            job.cancel()
        }
    }
}
```

이렇게 가독성 좋게 바꿀 수 있다.

onSuccess를 쓰지않고 그냥 리시버로 api를 호출하는 방법도 있는데, 그렇게 하면 성공했을 때 데이터를 this로만 접근할 수 있다. 나는 받아올 객체 데이터에다가 이름을 지정하는 게 가독성이 좋다고 생각해서 onSuccess를 사용하는 걸 선호한다.

개념은 이정도로 정리해봤는데, 코루틴과 함께 사용할 때 문제되는 부분을 잡아보겠다.

CompletableDeferred가 외부에서 값을 집어넣을 수 있어서 유연해 보이지만, 남발하면 오히려 상태 추적이 더 힘들어진다.

특히 여러 곳에서 complete/completeExceptionally를 동시에 시도하는 경우 race condition(누가 먼저 값을 넣냐)이 생긴다. 그래서 여러 곳에서 값을 넣기보다는 한 곳에서 넣는 게 더 좋을 것 같다는 생각을 했다.

## Job, Cancellation, Deferred

Deferred는 “값”을 리턴하는 미래의 작업이고, Job은 단순히 작업 단위(값은 없음)다.
이거 헷갈리는 사람 많다. Deferred를 취소하면 값 대기 중이던 쪽이 CancellationException을 바로 받는다.

즉, await() 중인 쪽에서 try-catch로 반드시 잡아야 한다. 취소 시 처리가 필요하다면 invokeOnCompletion이나 catch로 확실히 마무리하는 게 좋을 것 같다.

## CompletableDeferred와 async

async는 "내가 시킨 작업"을 비동기로 돌릴 때, CompletableDeferred는 "외부에서 값을 받아야 할 때, 직접 완료를 트리거해야 할 때"가 타겟이다.

즉, async는 내부에서 처리 흐름을 전부 내가 통제할 수 있을 때 적합하다.
예를 들어 계산, 네트워크, DB조회 등 “내가 시켜서 결과가 무조건 나오는” 작업에 딱 맞는다. 내부에서 예외가 나도 async로 돌린 코루틴 스코프에서만 핸들링하면 끝난다.

반대로, CompletableDeferred는 외부 콜백, 이벤트 리스너, 3rd party 라이브러리 등 내가 직접 결과를 push해야만 할 때 쓴다. 즉, 내가 “직접 complete를 불러야만” 값이 들어간다.

외부에서 언제 값이 들어올지, 어떻게 들어올지 알 수 없는 상황에서 유일하게 명시적으로 값을 넣고 작업을 끝낼 수 있다.

예를 들면

- 레거시 콜백 기반 API
- 알림/이벤트 콜백 처리
- 특정 비동기 신호가 들어올 때까지 기다렸다가 값 반환

이런 콜백 형태로 만들어진 패턴에서 무작정 async로 처리하면 안 되고, 반드시 CompletableDeferred로 명확하게 값을 컨트롤해야 동기화가 꼬이지 않는다.

나는 이번 사이드 프로젝트에 Firebase를 사용할 건데, 근데 문제는 파이어베이스가 저 세개 모두에 속한다는 것이다.

주로 사용하게 될 Realtime Database, Firestore, FCM은 이벤트가 발생할 때마다 콜백으로 데이터를 전달한다. 즉, 실시간 데이터 변경이나, 메시지 수신, 인증 상태 변경 등 “알림 기반 이벤트”가 들어올 때 콜백이 호출된다.

인증부분을 이번 포스팅에서 정리한 코루틴 메서드를 활용해서 구현해보겠다.

```kotlin
FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pw)
    .addOnCompleteListener { task ->
        if (task.isSuccessful) { 
            // 인증성공
        } else { 
            // 실패
        }
    }
```

이게 FirebaseAuth의 기본 구조다. 구글 로그인을 붙이면 코드가 훨씬 길어지는데, 일단 api만 보겠다.

`suspendCancellableCoroutine`을 사용해 suspend함수로 바꿔보자.

`suspendCancellableCoroutine`은 구조적으로 함수가 끝나면 continuation이 자동으로 닫히기 때문에 중복 완료를 신경 쓸 필요가 없다. 그래서 콜백 → suspend(단일 콜백, 단일 결과)로 바꿀 땐 굳이 객체를 만들 필요가 없으니까 `suspendCancellableCoroutine`이 더 적합하다 생각했다.

```kotlin
suspend fun signInSuspend(email: String, pw: String): AuthResult = suspendCancellableCoroutine { continuation ->
    val task = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pw)
    task.addOnCompleteListener { t ->
        if (t.isSuccessful) {
            continuation.resume(t.result!!)
        } else {
            continuation.resumeWithException(t.exception ?: Exception("Unknown error"))
        }
    }
}
```

하나 고민인 건 이렇게 단일 이벤트가 아닌, RealtimeDB, Firestore같은 여러 번 반복적으로 오는 콜백 처리를 어떻게 할지다.

이 부분은 실제 프로젝트에 적용하면서 개선해보겠다.