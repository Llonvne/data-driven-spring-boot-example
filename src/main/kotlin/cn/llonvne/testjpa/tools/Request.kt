package cn.llonvne.testjpa.tools

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.support.RequestContextUtils

fun currentRequest(): HttpServletRequest {
    val servletRequestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
    val request = servletRequestAttributes.request
    return request
}

fun requestWebApplication() = RequestContextUtils.findWebApplicationContext(currentRequest())!!