package cn.llonvne.example.db.token.service

import cn.llonvne.example.db.internal.token.entity.DbTokenEntity
import cn.llonvne.example.db.internal.token.service.InternalDbTokenEntityService
import cn.llonvne.example.db.result.*
import cn.llonvne.example.db.token.TokenInternalApi
import cn.llonvne.example.db.token.error.GetTokenQueryError
import cn.llonvne.example.db.token.error.NewTokenMutationError
import cn.llonvne.example.db.token.error.RemoveTokenMutationError
import cn.llonvne.example.db.token.mutation.NewTokenMutation
import cn.llonvne.example.db.token.mutation.RemoveTokenMutation
import cn.llonvne.example.db.token.query.GetTokenByTypeQuery
import cn.llonvne.example.db.token.query.GetTokenQuery
import cn.llonvne.example.db.token.type.Token
import org.springframework.stereotype.Service

@Service
@TokenInternalApi
class TokenService(
    private val service: InternalDbTokenEntityService,
) {
    fun <T : Token> save(mutation: NewTokenMutation<T>): MTR<T, NewTokenMutationError> {

        if (service.isTokenRawExist(mutation.token.token)) {
            return MutationTypedResult.None(NewTokenMutationError.TokenValueAlreadyExist)
        }

        val entity = DbTokenEntity(token = mutation.token.token, type = mutation.token.type.name)

        service.save(entity)

        return MutationTypedResult.One(mutation.token)
    }

    fun getEntity(query: GetTokenQuery): OneQueryTypedResult<DbTokenEntity, GetTokenQueryError> {
        val entity = service.getByTokenRawOrNull(query.tokenRaw)
            ?: return OneQueryTypedResult.None(GetTokenQueryError.TokenRawNotFound)

        return OneQueryTypedResult.One(entity)
    }

    fun remove(mutation: RemoveTokenMutation): MTR<Unit, RemoveTokenMutationError> {
        val entity =
            service.getByTokenRawOrNull(mutation.token.token) ?: return MTR_N(RemoveTokenMutationError.TokenNotExist)

        service.remove(entity)
        return MTR_O(Unit)
    }

    fun byType(query: GetTokenByTypeQuery): OneQueryResult<List<DbTokenEntity>> {
        return OneQueryResult.One(service.byType(query.type))
    }
}