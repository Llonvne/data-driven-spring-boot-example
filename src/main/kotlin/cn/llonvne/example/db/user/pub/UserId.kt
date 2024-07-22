package cn.llonvne.example.db.user.pub

import cn.llonvne.example.security.GuardTypeMarker

/**
 * a public representation of User domain.
 */
data class UserId(
    val id: String,
) : GuardTypeMarker