package cn.llonvne.example.db.user.mutation

import cn.llonvne.example.db.user.pub.UserId

/**
 * Represent a Update for [cn.llonvne.example.db.user.DbUser];
 *
 * * id must be exited in Database.
 * * password property is not exist in DbUser class.If you want to change it in database use [cn.llonvne.example.db.user.mutation.UpdateUserPasswordMutation]
 */
data class DbUserUpdateMutation(val userId: UserId)
