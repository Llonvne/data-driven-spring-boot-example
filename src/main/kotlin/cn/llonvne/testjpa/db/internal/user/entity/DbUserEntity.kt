package cn.llonvne.testjpa.db.internal.user.entity

import jakarta.persistence.*
import java.util.*

/**
 * 用于在JPA表示用户实体的的直接类型
 */
@Entity
@Table(name = "db_user")
data class DbUserEntity(
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    val id: String = UUID.randomUUID().toString(),

    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @ManyToMany
    @JoinTable(
        name = "user_follow",
        joinColumns = [JoinColumn(name = "follower_id")],
        inverseJoinColumns = [JoinColumn(name = "followee_id")]
    )
    val followees: Set<DbUserEntity> = setOf(),
)