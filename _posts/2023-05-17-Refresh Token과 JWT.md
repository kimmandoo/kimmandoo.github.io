---
categories: [Web]
tags: [json, jwt, web]
---

# Refresh Token과 JWT

[지난 게시물](https://kimmandoo.github.io/posts/JWT-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0/)에서 JWT에 대해 전체적으로 알아봤다. Access Token 역할에 대해서만 알아봤는데, 이제 Refresh Token 측면에서 살펴보겠다. 서버 메모리에 따로 저장되지 않고 토큰 그 자체로만 검증해 사용자 인증을 하는 Access Token은 토큰이 만료되기 전에는 토큰을 획득한 누구나 권한을 가질 수 있다.

JWT를 사용할 때 삭제가 안되기 때문에 토큰에 유효시간을 부여하는 식으로 수명관리를 하는데, 이때 Refresh Token을 사용하면 토큰의 유효기간을 짧게 가져가면서 로그인 횟수를 줄이는 방식이기 때문에 수명관리를 효율적으로 할 수 있다.

## Refresh Token

<img title="" src="https://images.ctfassets.net/cdy7uua7fh8z/3sf7RRsy81bt3zcXMnHUSe/2171fdab4ffeb0987c329aa897038abc/rt-and-at.png" alt="img" width="432" data-align="center">

> SPA = Single-Page Application; AS = Authorization Server; RS = Resource Server; AT = Access Token; RT = Refresh Token. 여기서 SPA를 사용자라고 생각하면된다.

Refresh Token은 재발급에 관여하는 토큰이다. 기존에 Access Token만 발급했다면, Refresh Token을 동시에 발급해서 Access Token의 exp 취약점을 막을 수 있다. 동시에 발급했고 같이사용되므로 두 개 모두 헤더에 담아서 보낸다. Refresh Token의 유효기간을 비교적 길게, Access Token의 유효기간을 짧게 해서 권한 탈취위협을 줄일 수 있다.

만료된 Access Token을 서버에 보내면, 아직 유효한 Refresh Token을 서버에 등록되어있는 Refresh Token과 비교해서 일치하면 Access Token을 재발급한다. 사용자가 더이상 접근하지 않는다면(로그아웃) 서버의 저장소에서 Refresh Token을 삭제해서 Access Token 자체를 못쓰게 하고, 재 로그인 시 다시 Refresh Token을 발급해서 이것만 서버 사용자DB에 저장하게 된다.

일반적으로 Refresh Token이 만료되었다는 말은 재로그인이 필요하다는 말이다. 재로그인이 귀찮다고 Refresh의 유효기간을 길게하는 것은 취약점이다.

그럼 이쯤에서 생기는 의문. ***Refresh Token도 탈취 위험이 있지않은가?*** 이거는 뒤에서 다시 보겠다.

### 인증과정

1. 사용자가 로그인해서 서버가 DB 정보를 이용해 인증을 수행한다. 인증이 끝나면 Access, Refresh 토큰을 발급해서 서버가 사용자에게 보낸다. 서버에는 Refresh Token만 남는다.

2. 사용자는 Access Token으로 API 요청을 수행한다. 이때 Access Token이 유효하면 API 요청이 잘 수행된다.

3. Access Token이 만료되었다면 서버가 만료신호를 사용자에게 보낸다. 그 전에 사용자는 Access Token의 페이로드에 있는 EXP를 보고 유효기간을 알수있으므로 재발급 요청시기를 예측할 수 있다. 만료가 되면 Access Token 발급요청을 사용자가 서버에게로 하게 되는데, 서버는 Refresh Token이 유효하다면 확인후 발급해준다. 만약 access는 유효하고 refresh가 만료된 경우에는 **access token으로 refresh token**을 재발급한다.

### 취약점

이건 거의 Access Token과 비슷한데, 탈취위험의 관점이 살짝 다르다. Access Token을 탈취해도 이게 만료되면 못쓰는 Refresh Token 혼용법의 특성상, 공격자는 Refresh Token을 탈취하려고 할 것이다. 이를 피하려면 안전한 저장소(암호화, 감사,로깅시스템이 도입된 저장소 등등)에 이 토큰을 저장하는 수 밖에 없다. 

### 예시 코드 보기

```kotlin
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

class TokenManager(private val secretKey: String) {
    private val accessTokenExpiration = 15 * 60 * 1000 // 15분
    private val refreshTokenExpiration = 7 * 24 * 60 * 60 * 1000 // 7일

    // 액세스 토큰 발급
    fun generateAccessToken(userId: String): String {
        return Jwts.builder()
            .setSubject(userId)
            .setExpiration(Date(System.currentTimeMillis() + accessTokenExpiration))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    // 리프레시 토큰 발급
    fun generateRefreshToken(userId: String): String {
        return Jwts.builder()
            .setSubject(userId)
            .setExpiration(Date(System.currentTimeMillis() + refreshTokenExpiration))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    // 토큰 검증
    fun verifyToken(token: String): String? {
        val claims: Claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body

        return claims.subject
    }
}

// 사용 예시
fun main() {
    val secretKey = "your-secret-key"
    val tokenManager = TokenManager(secretKey)

    val userId = "12345"

    // 액세스 토큰 발급
    val accessToken = tokenManager.generateAccessToken(userId)
    println("Access Token: $accessToken")

    // 리프레시 토큰 발급
    val refreshToken = tokenManager.generateRefreshToken(userId)
    println("Refresh Token: $refreshToken")

    // 토큰 검증
    val verifiedUserId = tokenManager.verifyToken(accessToken)
    if (verifiedUserId != null && verifiedUserId == userId) {
        println("Access Token is valid.")
    } else {
        println("Access Token is invalid.")
    }
}
```

GPT가 생성해준 JWT라이브러리를 사용해서 토큰을 처리하는 코드다. 액세스는 15분, 리프레시는 7일 간 유효하다. JWT를 아예 구현할 줄 몰랐는데 구조만 알아도 사용법이 좀 보인다.



---

> 참조: 
> 
> [[WEB] Access Token &amp; Refresh Token 이해 with JWT](https://jungjin.oopy.io/41d894e3-ca5f-43dc-978c-f6dec9edc467)
> 
> https://velog.io/@park2348190/JWT%EC%97%90%EC%84%9C-Refresh-Token%EC%9D%80-%EC%99%9C-%ED%95%84%EC%9A%94%ED%95%9C%EA%B0%80
> 
> [What Are Refresh Tokens and How to Use Them Securely](https://auth0.com/blog/refresh-tokens-what-are-they-and-when-to-use-them/)
> 
> 