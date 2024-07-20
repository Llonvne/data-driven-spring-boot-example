package cn.llonvne.example.db.token.mutation

import cn.llonvne.example.db.token.type.Token

class NewTokenMutation<T : Token>(val token: T)