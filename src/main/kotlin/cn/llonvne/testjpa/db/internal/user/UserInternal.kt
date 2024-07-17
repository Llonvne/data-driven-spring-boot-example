package cn.llonvne.testjpa.db.internal.user

/**
 * [cn.llonvne.testjpa.db.internal.user] This package and its sub-packages are used to maintain the state of the User entity.
 *
 * * All APIs within the db.internal should not be referenced outside the db package.
 */
object UserInternal {

}

@RequiresOptIn(message = "user internal api is not supported to use out of db.internal.user package")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class UserInternalApi