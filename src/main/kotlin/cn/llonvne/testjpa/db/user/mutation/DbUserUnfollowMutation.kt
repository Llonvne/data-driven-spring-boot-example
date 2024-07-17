package cn.llonvne.testjpa.db.user.mutation

data class DbUserUnfollowMutation(val follower: String, val followee: String)
