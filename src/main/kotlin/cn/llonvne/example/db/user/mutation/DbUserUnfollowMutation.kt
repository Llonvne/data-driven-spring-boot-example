package cn.llonvne.example.db.user.mutation

/**
 * Represent a mutation that make a user unfollowing another user.
 */
data class DbUserUnfollowMutation(val follower: String, val followee: String)
