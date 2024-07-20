package cn.llonvne.example.db.internal.token.entity

import cn.llonvne.example.db.token.TokenInternalApi
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tb_token")
@TokenInternalApi
data class DbTokenEntity(
    @Id
    @Column(name = "token", nullable = false, unique = true)
    val token: String,

    @Column(name = "type", nullable = false)
    val type: String
)