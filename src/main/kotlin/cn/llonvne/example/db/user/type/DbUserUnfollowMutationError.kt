package cn.llonvne.example.db.user.type

enum class DbUserUnfollowMutationError {
    FollowerIdNotExist,
    FolloweeIdNotExist,
    NotFollowed
}