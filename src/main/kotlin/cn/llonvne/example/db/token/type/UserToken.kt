package cn.llonvne.example.db.token.type

class UserToken(
    override val token: String
) : Token {
    override val type: TokenTypeEnum = TokenTypeEnum.USER
}