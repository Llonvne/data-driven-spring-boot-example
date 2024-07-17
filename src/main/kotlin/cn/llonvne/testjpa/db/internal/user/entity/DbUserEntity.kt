package cn.llonvne.testjpa.db.internal.entity

import jakarta.persistence.*
import java.util.UUID

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
    val password: String
)