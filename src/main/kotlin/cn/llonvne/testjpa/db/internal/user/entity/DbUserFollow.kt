package cn.llonvne.testjpa.db.internal.user.entity

import jakarta.persistence.*
import java.io.Serializable

@Entity
@IdClass(DbUserFollowIdType::class)
@Table(name = "user_follow")
data class DbUserFollow(
    @Id
    @Column(name = "follower_id", nullable = false)
    val userId: String,

    @Id
    @Column(name = "followee_id", nullable = false)
    val followeeId: String
)

data class DbUserFollowIdType(
    val userId: String = "",
    val followeeId: String = ""
) : Serializable
