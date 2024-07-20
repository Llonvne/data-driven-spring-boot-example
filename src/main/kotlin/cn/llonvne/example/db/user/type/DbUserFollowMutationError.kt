package cn.llonvne.example.db.user.type

enum class DbUserFollowMutationError {
    FollowerIdNotExist,
    FolloweeIdNotExist,
    AlreadyFollowed
}