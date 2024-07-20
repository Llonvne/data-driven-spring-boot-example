package cn.llonvne.example.db.internal.user

/**
 * [cn.llonvne.example.db.internal.user] This package and its sub-packages are used to maintain the state of the User entity.
 *
 * * All APIs within the db.internal should not be referenced outside the db package.
 */
object UserInternal

@RequiresOptIn(message = "user internal api is not supported to use out of db.internal.user package")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class UserInternalApi

//@RequiresOptIn("the shorthand typealias is really disgusting.please don't use it util the result type is everywhere.")
@Target(AnnotationTarget.TYPEALIAS)
annotation class DisgustingQueryMutationResultShorthandApi