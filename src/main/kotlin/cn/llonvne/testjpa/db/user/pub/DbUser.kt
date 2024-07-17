package cn.llonvne.testjpa.db.user.pub

import cn.llonvne.testjpa.security.GuardTypeMarker

/**
 * a public representation of User domain.
 */
data class DbUser(
    val id: String,
    val username: String,
) : GuardTypeMarker