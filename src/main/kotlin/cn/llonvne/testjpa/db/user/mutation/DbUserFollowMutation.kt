package cn.llonvne.testjpa.db.user.mutation

/**
 * Represent a mutation that make a user following another user.
 */
data class DbUserFollowMutation(val follower: String, val followee: String)
