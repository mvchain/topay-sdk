package com.mvc.pay_lib

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler

import android.os.Looper
import android.util.Log
import android.webkit.JavascriptInterface
import com.mvc.pay_lib.bean.UserInfo
import org.json.JSONObject
import java.lang.ref.WeakReference

class WebJavaScriptAndAndroid(private val mainActivity: PayWebActivity) {

    private lateinit var userInfo: UserInfo
    private var uiHandler: Handler = Handler(Looper.getMainLooper())
    private var sp: SharedPreferences

    init {
        var activity = WeakReference<Activity>(mainActivity)
        sp = activity.get()?.getSharedPreferences("userInfo", Context.MODE_PRIVATE)!!
        userInfo = UserInfo(
                sp.getString("email", "")
                , sp.getInt("isBusinesses", 0)
                , sp.getString("refreshToken", "")
                , sp.getString("salt", "")
                , sp.getString("token", "")
                , sp.getInt("userId", 0))
    }

    @JavascriptInterface
    fun setToken(obj: String) {
        var tokenObj = JSONObject(obj)
        userInfo = UserInfo(
                tokenObj.getString("email")
                , tokenObj.getInt("isBusinesses")
                , tokenObj.getString("refreshToken")
                , tokenObj.getString("salt")
                , tokenObj.getString("token")
                , tokenObj.getInt("userId")
        )
        var editor = sp.edit()
        editor.putString("email", tokenObj.getString("email"))
        editor.putInt("isBusinesses", tokenObj.getInt("isBusinesses"))
        editor.putString("refreshToken", tokenObj.getString("refreshToken"))
        editor.putString("salt", tokenObj.getString("salt"))
        editor.putString("token", tokenObj.getString("token"))
        editor.putInt("userId", tokenObj.getInt("userId"))
        editor.commit()
    }

    @JavascriptInterface
    fun getAdmin(): String {
        var orderInfo = mainActivity.intent.getStringExtra("orderInfo")
        var orderJson = JSONObject(orderInfo).getJSONObject("data")
        var serviceJson = JSONObject()
        serviceJson.put("refreshToken", userInfo.refreshToken)
                .put("token", userInfo.token)
                .put("order", JSONObject().put("amount", orderJson.getDouble("amount"))
                        .put("buyUsername", orderJson.getString("buyUsername"))
                        .put("limitTime", orderJson.getLong("limitTime"))
                        .put("orderNumber", orderJson.getString("orderNumber"))
                        .put("payAccount", orderJson.getString("payAccount"))
                        .put("payType", orderJson.getInt("payType"))
                        .put("price", orderJson.getDouble("price"))
                        .put("remitShopId", orderJson.getInt("remitShopId"))
                        .put("remitUserId", orderJson.getInt("remitUserId"))
                        .put("sellUsername", orderJson.getString("sellUsername"))
                        .put("shopId", orderJson.getInt("shopId"))
                        .put("sign", orderJson.getString("sign"))
                        .put("tokenId", orderJson.getString("tokenId"))
                        .put("tokenValue", orderJson.getDouble("tokenValue"))
                        .put("tokenName", orderJson.getString("tokenName"))
                )
        return serviceJson.toString()
//
//        OrderInfo(userInfo.refreshToken
//                , userInfo.token
//                , OrderBean(orderJson.getDouble("amount")
//                , orderJson.getString("buyUsername")
//                , orderJson.getLong("limitTime")
//                , orderJson.getString("orderNumber")
//                , orderJson.getString("payAccount")
//                , orderJson.getInt("payType")
//                , orderJson.getDouble("price")
//                , orderJson.getInt("remitShopId")
//                , orderJson.getInt("remitUserId")
//                , orderJson.getString("sellUsername")
//                , orderJson.getInt("shopId")
//                , orderJson.getString("sign")
//                , orderJson.getString("tokenId")
//                , orderJson.getDouble("tokenValue")
//                , orderJson.getString("tokenName")
//        )
//        )
    }
}
