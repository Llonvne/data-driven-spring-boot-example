package cn.llonvne.testjpa.security.internal

import cn.llonvne.testjpa.security.Guard
import cn.llonvne.testjpa.security.GuardType
import cn.llonvne.testjpa.security.Protected
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@SecurityInternalApi
@Aspect
@Component
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