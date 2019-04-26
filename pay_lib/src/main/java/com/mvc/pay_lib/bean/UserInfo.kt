package com.mvc.pay_lib.bean

data class UserInfo(
        var email: String,
        var isBusinesses: Int,
        var refreshToken: String,
        var salt: String,
        var token: String,
        var userId: Int
)