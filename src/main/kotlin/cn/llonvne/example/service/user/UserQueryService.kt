package cn.llonvne.example.service.user

import cn.llonvne.example.db.user.DbUserService
import cn.llonvne.example.db.user.pub.UserId
import cn.llonvne.example.db.user.query.DbUsernameQuery
import cn.llonvne.example.service.response.OneResponse
import cn.llonvne.example.service.response.one
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Service
class UserQueryService(
    private val dbUserService: DbUserService
) {
    fun username(userID: UserId): OneResponse<String> {
        return dbUserService.username(DbUsernameQuery(userID)).one(noneStatus = HttpStatus.SERVICE_UNAVAILABLE)
    }
}