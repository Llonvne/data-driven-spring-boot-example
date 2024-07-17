package cn.llonvne.testjpa.db.user.type

enum class DbUserUnfollowMutationError {
    FollowerIdNotExist,
    FolloweeIdNotExist,
    NotFollowed
}