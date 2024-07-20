package cn.llonvne.example.db.internal.user.entity

import cn.llonvne.example.db.internal.user.UserInternalApi
import jakarta.persistence.*
import java.io.Serializable

/**
 * Represents a Join Table of User which maps the relationship between users who follow each other.
 */
@Entity
@IdClass(DbUserFollowIdType::class)
@Table(name = "user_follow")
@UserInternalApi
data class DbUserFollow(
    @Id
    @Column(name = "follower_id", nullable = false)
    val userId: String,

    @Id
    @Column(name = "followee_id", nullable = false)
    val followeeId: String
)

/**
 * Represents the composite primary key of the `user_follow` table.
 */
@UserInternalApi
data class DbUserFollowIdType(
    val userId: String = "",
    val followeeId: String = ""
) : Serializable
