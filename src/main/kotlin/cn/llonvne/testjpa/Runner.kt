package cn.llonvne.testjpa

import cn.llonvne.testjpa.db.user.DbUserService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class Runner(private val dbUserService: DbUserService):ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
    }
}