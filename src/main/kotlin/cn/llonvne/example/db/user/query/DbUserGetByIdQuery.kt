package cn.llonvne.example.db.user.query

/**
 * Represent a query for [cn.llonvne.example.db.user.pub.DbUser] of a specific userId
 */
data class DbUserGetByIdQuery(val userId: String)