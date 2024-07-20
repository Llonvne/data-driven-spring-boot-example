package cn.llonvne.example.db.internal.user.entity

import cn.llonvne.example.db.internal.user.UserInternalApi
import jakarta.persistence.*
import java.util.*

/**
 * Represent a User Entity that interact with Spring Data JPA
 */
@Entity
@Table(name = "db_user")
@UserInternalApi
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