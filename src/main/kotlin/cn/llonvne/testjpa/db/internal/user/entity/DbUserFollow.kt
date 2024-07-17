package cn.llonvne.testjpa.db.internal.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.io.Serializable

@Entity
@IdClass(DbUserFollowIdType::class)
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
