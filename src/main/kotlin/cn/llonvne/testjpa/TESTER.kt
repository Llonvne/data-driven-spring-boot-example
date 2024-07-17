package cn.llonvne.testjpa

import cn.llonvne.testjpa.db.result.MutationResult
import cn.llonvne.testjpa.db.result.MutationResult.Success
import cn.llonvne.testjpa.db.result.assertSuccess
import cn.llonvne.testjpa.db.result.unwrap
import cn.llonvne.testjpa.db.user.DbUserService
import cn.llonvne.testjpa.db.user.mutation.DbUserFollowMutation
import cn.llonvne.testjpa.db.user.mutation.DbUserNewMutation
import cn.llonvne.testjpa.db.user.mutation.DbUserUnfollowMutation
import cn.llonvne.testjpa.db.user.query.DbUserFolloweesQuery
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Service

@Service
class TESTER(
    private val service: DbUserService
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val a = service.newUser(DbUserNewMutation("a", "123")).unwrap()
        val b = service.newUser(DbUserNewMutation("b", "123")).unwrap()
        val c = service.newUser(DbUserNewMutation("c", "123")).unwrap()
        service.follow(DbUserFollowMutation(a.id, b.id))
        service.follow(DbUserFollowMutation(a.id, c.id))
        println(service.followees(DbUserFolloweesQuery(a.id)))

        service.unfollow(DbUserUnfollowMutation(a.id, b.id))
        println(service.followees(DbUserFolloweesQuery(a.id)))
    }
}