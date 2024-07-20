package cn.llonvne.example.req2resp

import cn.llonvne.example.req2resp.validate.ValidatableRequest
import cn.llonvne.example.req2resp.validate.ofUUID
import cn.llonvne.example.req2resp.validate.validate

data class UserFollowRequest(val followerId: String) : ValidatableRequest<UserFollowRequest> {
    override fun validate() = validate {
        ofUUID(::followerId)
    }
}

data class UserUnfollowRequest(val followerId: String) : ValidatableRequest<UserUnfollowRequest> {
    override fun validate() = validate {
        ofUUID(::followerId)
    }
}