package cn.llonvne.testjpa.db.user.mutation

import cn.llonvne.testjpa.db.user.DbUser

/**
 * Represent a Update for [cn.llonvne.testjpa.db.user.DbUser];
 *
 * * id must be exited in Database.
 * * password property is not exist in DbUser class.If you want to change it in database use [cn.llonvne.testjpa.db.user.mutation.UpdateUserPasswordMutation]
 */
data class UpdateUserMutation(val dbUser: DbUser)
