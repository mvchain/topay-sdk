package com.mvc.pay_lib.bean

data class OrderInfo(
        var refreshToken: String,
        var token: String,
        var order: OrderBean

) {
    data class OrderBean(
            var amount: Double,
            var buyUsername: String,
            var limitTime: Long,
            var orderNumber: String,
            var payAccount: String,
            var payType: Int,
            var price: Double,
            var remitShopId: Int,
            var remitUserId: Int,
            var sellUsername: String,
            var shopId: Int,
            var sign: String,
            var tokenId: String,
            var tokenValue: Double,
            var tokenName: String
    )
}
