package cn.llonvne.example.db.internal.user.converter

import cn.llonvne.example.db.internal.EntityConverter
import cn.llonvne.example.db.internal.user.UserInternalApi
import cn.llonvne.example.db.internal.user.entity.DbUserEntity
import cn.llonvne.example.db.user.pub.UserId
import org.springframework.stereotype.Component

/**
 * a Converter that converter DbUserEntity to DbUser
 */
@Component
@UserInternalApi
object DbUserEntityToDbUserConverter : EntityConverter<DbUserEntity, UserId> {
    override fun convert(input: DbUserEntity): UserId {
        return UserId(id = input.id)
    }
}