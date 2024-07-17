package cn.llonvne.testjpa.db.user.query

/**
 * Represent a query for [cn.llonvne.testjpa.db.user.pub.DbUser] of a specific userId
 */
data class DbUserGetByIdQuery(val userId: String)