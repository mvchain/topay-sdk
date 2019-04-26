package com.mvc.to_pay_sdk

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import cn.jpush.android.api.JPushInterface
import com.mvc.to_pay_sdk.listener.IDialogCallback
import kotlinx.android.synthetic.main.activity_inpour.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class InpourActivity : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var editUser :SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inpour)
        init()
        initPrice()
    }

    private fun init() {
        sp = getSharedPreferences("uuid", Context.MODE_PRIVATE)
        editUser = sp.edit()
        if (sp.getString("uuid", "") == "") {
            editUser.putString("uuid", UUID.randomUUID().toString().replace("-", "").substring(0, 4).toUpperCase())
            editUser.commit()
            JPushInterface.setAlias(baseContext, 18, sp.getString("uuid", ""))
        }
        inpour_submit.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java)
                    .putExtra("price_number", all_price.text.toString()))
        }
        MyApplication.setDialogCallback(object : IDialogCallback {
            override fun showDialog(msg: String) {
                var dialogLayout = LayoutInflater.from(baseContext).inflate(R.layout.layout_dialog, null, false)
                var alert = AlertDialog.Builder(this@InpourActivity)
                        .setView(dialogLayout)
                        .show()
                dialogLayout.findViewById<TextView>(R.id.dialog_submit).setOnClickListener { alert.dismiss() }
                dialogLayout.findViewById<TextView>(R.id.dialog_content).text = msg
                initPrice()
            }
        })
    }

    private fun initPrice() {
        var moneyAddress = "http://47.110.144.216/api/simulation/order/total?uid=${sp.getString("uuid", "")}"
        Thread(Runnable {
            try {
                var url = URL(moneyAddress)
                var httpConnection = url.openConnection() as HttpURLConnection
                httpConnection.requestMethod = "GET"
                httpConnection.setRequestProperty("Content-Type", "application/json")
                httpConnection.addRequestProperty("encoding", "UTF-8")
                httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                httpConnection.setRequestProperty("accept", "application/json")
                httpConnection.connectTimeout = 5000
                httpConnection.readTimeout = 5000
                httpConnection.connect()
                if (httpConnection.responseCode == 200) {
                    var input = httpConnection.inputStream
                    var buff = BufferedReader(InputStreamReader(input))
                    var json = StringBuffer()
                    var temp: String
                    while (buff.readLine().also { temp = it ?: "" } != null) {
                        json.append(temp)
                    }
                    input.close()
                    httpConnection.disconnect()
                    var orderJson = JSONObject(json.toString())
                    var price = orderJson.getInt("data")
                    runOnUiThread {
                        all_price.text = "$price"
                    }
                } else {
                    var input = httpConnection.errorStream
                    var buff = BufferedReader(InputStreamReader(input))
                    var json = StringBuffer()
                    var temp: String
                    while (buff.readLine().also { temp = it ?: "" } != null) {
                        json.append(temp)
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "exception${e.message}")
            }
        }).start()
    }
}