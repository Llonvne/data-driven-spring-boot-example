package cn.llonvne.testjpa.db.user.type

enum class DbUserFollowMutationError {
    FollowerIdNotExist,
    FolloweeIdNotExist,
    AlreadyFollowed
}