package cn.llonvne.example.aop

import org.aspectj.lang.annotation.Pointcut

/**
 * Pointcut to match all methods within the cn.llonvne.testjpa.service package
 */
@Pointcut("within(cn.llonvne.example.service)")
fun inServiceLayer() {
}

/**
 * Pointcut to exclude all methods within any internal package and its sub-packages
 */
@Pointcut("!within(*..internal..*)")
fun ignoreInternalPackage() {
}

/**
 * Pointcut to include all methods annotated a Protected Annotation
 */
@Pointcut("@annotation(cn.llonvne.example.security.Protected)")
fun protected() {
}
