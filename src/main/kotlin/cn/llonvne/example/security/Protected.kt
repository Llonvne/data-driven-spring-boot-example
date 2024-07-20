package cn.llonvne.example.security

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Protected(val method: GuardType)