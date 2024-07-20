package cn.llonvne.example.db.internal.user.converter

import cn.llonvne.example.db.internal.EntityConverter
import cn.llonvne.example.db.internal.user.UserInternalApi
import cn.llonvne.example.db.internal.user.entity.DbUserEntity
import cn.llonvne.example.db.user.pub.DbUser
import org.springframework.stereotype.Component

/**
 * a Converter that converter DbUserEntity to DbUser
 */
@Component
@UserInternalApi
object DbUserEntityToDbUserConverter : EntityConverter<DbUserEntity, DbUser> {
    override fun convert(input: DbUserEntity): DbUser {
        return DbUser(id = input.id, username = input.username)
    }
}