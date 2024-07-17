package cn.llonvne.testjpa.db.user.type

enum class DbUserUpdatePasswordMutationError {
    IdNotExist,
    OldPasswordNotCorrect
}