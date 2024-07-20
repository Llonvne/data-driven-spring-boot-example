package cn.llonvne.example.db.token.mutation

import cn.llonvne.example.db.user.pub.DbUser

data class GenerateAndSaveTokenMutation(val dbUser: DbUser)
