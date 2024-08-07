package cn.llonvne.example.security.internal

import cn.llonvne.example.const.AopOrder
import cn.llonvne.example.security.Guard
import cn.llonvne.example.security.GuardType
import cn.llonvne.example.security.Protected
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@SecurityInternalApi
@Aspect
@Component
@Order(AopOrder.Guard)
class ProtectedResolver(
    private val initialGuards: List<Guard<*>>
) {

    private val guards: Map<GuardType, Guard<*>> = initialGuards.associateBy { it.name }

    @Around("@annotation(anno)")
    fun protected(joinPoint: ProceedingJoinPoint, anno: Protected): ResponseEntity<*> {

        val servletRequestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request = servletRequestAttributes.request

        val guard = guards[anno.method]
        if (guard?.pass(request) != null) {
            return ResponseEntity<String?>(HttpStatus.UNAUTHORIZED)
        }

        val ret = joinPoint.proceed()
        return ret as ResponseEntity<*>
    }
}