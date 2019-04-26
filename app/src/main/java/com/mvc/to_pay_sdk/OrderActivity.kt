package com.mvc.to_pay_sdk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.mvc.to_pay_sdk.adapter.OrderAdapter
import com.mvc.to_pay_sdk.bean.OrderBean
import kotlinx.android.synthetic.main.activity_order.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class OrderActivity : AppCompatActivity() {
    private lateinit var orderBean: ArrayList<OrderBean>
    private lateinit var orderAdapter: OrderAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        initHttp()
    }

    private fun initHttp() {
        orderBean = ArrayList()
//        orderAdapter = OrderAdapter(baseContext, R.layout.item_order, orderBean)
        Thread(Runnable {
            orderBean.clear()
            var url = URL("http://47.110.144.216/api/simulation/order?uid=${intent.getStringExtra("uuid")}")
            var httpConnection = url.openConnection() as HttpURLConnection
            httpConnection.requestMethod = "GET"
            httpConnection.setRequestProperty("Content-Type", "application/json")
            httpConnection.addRequestProperty("encoding", "UTF-8")
            httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
            httpConnection.addRequestProperty("accept", "application/json")
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
                var orderArray = orderJson.getJSONArray("data")
                for (position in 0 until orderArray.length() - 1) {
                    var obj = orderArray.getJSONObject(position)
                    orderBean.add(OrderBean(obj.getInt("cny")
                            , obj.getLong("createdAt")
                            , obj.getString("orderNumber")
                            , obj.getInt("cny")
                    ))
                }
                runOnUiThread {
                    if (orderBean.isEmpty()) {
                        order_rv.visibility = View.INVISIBLE
                        order_null.visibility = View.VISIBLE
                    } else {
                        order_rv.visibility = View.VISIBLE
                        order_null.visibility = View.INVISIBLE
                        orderAdapter.notifyDataSetChanged()
                    }
                }
            }
        }).start()
        order_rv.adapter = orderAdapter
    }
}
