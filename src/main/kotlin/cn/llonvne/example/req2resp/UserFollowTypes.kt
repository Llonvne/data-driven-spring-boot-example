package cn.llonvne.example.req2resp

data class UserFollowRequest(val followerId: String)

data class UserUnfollowRequest(val followerId: String)