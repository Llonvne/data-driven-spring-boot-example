package cn.llonvne.example.db.user.pub

import cn.llonvne.example.security.GuardTypeMarker

/**
 * a public representation of User domain.
 */
data class DbUser(
    val id: String,
    val username: String,
) : GuardTypeMarker