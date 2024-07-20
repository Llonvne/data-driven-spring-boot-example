package cn.llonvne.example.db.token.query

import cn.llonvne.example.db.token.type.TokenTypeEnum

class GetTokenQuery(val tokenRaw: String)

class GetTokenByTypeQuery(val type: TokenTypeEnum)