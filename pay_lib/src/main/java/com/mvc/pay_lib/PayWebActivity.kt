package com.mvc.pay_lib

import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import android.webkit.*
import kotlinx.android.synthetic.main.activity_pay.*

class PayWebActivity : AppCompatActivity() {
    private lateinit var payWeb: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        payWeb = WebView(this)
        web_layout.addView(initWeb(payWeb))
        payWeb.loadUrl("http://47.110.144.216/m-topay/#/")
    }

    private fun initWeb(payWeb: WebView): WebView {
        payWeb.settings.domStorageEnabled = true
        payWeb.settings.javaScriptEnabled = true
        payWeb.addJavascriptInterface(WebJavaScriptAndAndroid(this), "mvc")
        payWeb.webViewClient = object : WebViewClient() {
//
        }
        payWeb.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return false
            }
        }
        return payWeb
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setTitle("提示：")
                .setMessage("要继续完成充值，请勿关闭此页面！")
                .setPositiveButton("继续支付", DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                    return@OnClickListener
                }).setNegativeButton("关闭页面") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }.show()
    }

    override fun onDestroy() {
        payWeb.clearHistory()
        (payWeb.parent as ViewGroup).removeView(payWeb)
        payWeb.stopLoading()
        payWeb.settings.domStorageEnabled = false
        payWeb.settings.javaScriptEnabled = false
        payWeb.clearView()
        payWeb.removeAllViews()
        payWeb.destroy()
        super.onDestroy()
    }
}
