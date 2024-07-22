package cn.llonvne.example

import cn.llonvne.example.jwt.JwtInternalApi
import cn.llonvne.example.jwt.internal.JwtInternal
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
class SpringBootExampleApplication


@Configuration
@EnableAspectJAutoProxy
class AppConfig {
    @OptIn(JwtInternalApi::class)
    @Bean
    fun jwt(@Value("\${pd.jwt.secret}") secret: String) = JwtInternal(secret)
}

fun main(args: Array<String>) {
    runApplication<SpringBootExampleApplication>(*args)
}
