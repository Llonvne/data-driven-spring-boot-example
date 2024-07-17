package cn.llonvne.testjpa.aop

import org.aspectj.lang.annotation.Pointcut

/**
 * Pointcut to match all methods within the cn.llonvne.testjpa.service package
 */
@Pointcut("within(cn.llonvne.testjpa.service)")
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
@Pointcut("@annotation(cn.llonvne.testjpa.security.Protected)")
fun protected() {
}