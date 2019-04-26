package com.mvc.to_pay_sdk

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.mvc.to_pay_sdk.listener.IDialogCallback

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        JPushInterface.init(this)
    }

    companion object {
        private lateinit var dialogCallback: IDialogCallback

        fun setDialogCallback(dialogCallback: IDialogCallback) {
            this.dialogCallback = dialogCallback
        }

        fun showDialog(msg: String) {
            dialogCallback.showDialog(msg)
        }
    }
}