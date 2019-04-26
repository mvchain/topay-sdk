package com.mvc.to_pay_sdk.bean

data class OrderBean(
        var cny: Int,
        var createdAt: Long,
        var orderNumber: String,
        var status: Int
)