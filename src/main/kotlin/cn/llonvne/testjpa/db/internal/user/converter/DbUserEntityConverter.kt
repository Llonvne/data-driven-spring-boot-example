package cn.llonvne.testjpa.db.internal.user.converter

import cn.llonvne.testjpa.db.internal.EntityConverter
import cn.llonvne.testjpa.db.internal.user.entity.DbUserEntity
import cn.llonvne.testjpa.db.user.DbUser
import org.springframework.stereotype.Component

@Component
object DbUserEntityConverter : EntityConverter<DbUserEntity, DbUser> {
    override fun convert(input: DbUserEntity): DbUser {
        return DbUser(id = input.id, username = input.username)
    }
}