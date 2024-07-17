package cn.llonvne.testjpa

import cn.llonvne.testjpa.db.internal.user.services.InternalDbUserEntityService
import cn.llonvne.testjpa.jwt.JwtInternalApi
import cn.llonvne.testjpa.jwt.internal.JwtInternal
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
class TestJpaApplication


@Configuration
@EnableAspectJAutoProxy
class AppConfig {
    @OptIn(JwtInternalApi::class)
    @Bean
    fun jwt(@Value("\${pd.jwt.secret}") secret: String) = JwtInternal(secret)
}

fun main(args: Array<String>) {
    runApplication<TestJpaApplication>(*args)
}
