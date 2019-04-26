package com.mvc.to_pay_sdk

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cn.jpush.android.api.JPushInterface
import com.mvc.pay_lib.PayWebActivity
import com.mvc.to_pay_sdk.adapter.BlockAdapter
import com.mvc.to_pay_sdk.bean.BlockBean
import com.mvc.to_pay_sdk.listener.BlockOnClickListener
import com.mvc.to_pay_sdk.listener.IDialogCallback
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var editUser :SharedPreferences.Editor
    private lateinit var blockAdapter: BlockAdapter
    private lateinit var blockBean: ArrayList<BlockBean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initRandomData()
    }

    private fun initRandomData() {
        blockBean.add(BlockBean(6
                , System.currentTimeMillis()
                , 0
                , "LJ9965845848B"
                , 0
                , 0
                , System.currentTimeMillis()
                , "6"
                , "6"
                , 6
                , 0
                , R.drawable.six_dollar))

        blockBean.add(BlockBean(30
                , System.currentTimeMillis()
                , 0
                , "LJ22859648A"
                , 0
                , 0
                , System.currentTimeMillis()
                , "30"
                , "30"
                , 30
                , 0
                , R.drawable.thirty_dollar))

        blockBean.add(BlockBean(68
                , System.currentTimeMillis()
                , 0
                , "LJ859658452C"
                , 0
                , 0
                , System.currentTimeMillis()
                , "68"
                , "68"
                , 68
                , 0
                , R.drawable.sixty_eight_dollar))

        blockBean.add(BlockBean(128
                , System.currentTimeMillis()
                , 0
                , "LJ5589958654C"
                , 0
                , 0
                , System.currentTimeMillis()
                , "128"
                , "128"
                , 128
                , 0
                , R.drawable.one_two_enght_dollar))

        blockBean.add(BlockBean(328
                , System.currentTimeMillis()
                , 0
                , "LJ8695848548C"
                , 0
                , 0
                , System.currentTimeMillis()
                , "328"
                , "328"
                , 328
                , 0
                , R.drawable.three_two_enght))

        blockBean.add(BlockBean(648
                , System.currentTimeMillis()
                , 0
                , "LJ1588984586Q"
                , 0
                , 0
                , System.currentTimeMillis()
                , "648"
                , "648"
                , 648
                , 0
                , R.drawable.six_four_enght_dollar))
        blockAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun init() {
        all_diamond.text = intent.getStringExtra("price_number")
        sp = getSharedPreferences("uuid", Context.MODE_PRIVATE)
        editUser = sp.edit()
        blockBean = ArrayList()
        blockAdapter = BlockAdapter(baseContext, R.layout.item_buying_coins, blockBean)
        blockAdapter.setBlockOnClickListener(object : BlockOnClickListener {
            override fun onClick(view: View, position: Int) {
                var httpAddress = "http://47.110.144.216/api/simulation/order"
                when (view.id) {
                    R.id.coins_layout -> {
                        Thread(Runnable {
                            try {
                                var url = URL(httpAddress)
                                var httpConnection = url.openConnection() as HttpURLConnection
                                httpConnection.requestMethod = "POST"
                                httpConnection.doOutput = true
                                httpConnection.doInput = true
                                var json = "{\"cny\":${blockBean[position].amount},\"uid\":\"${sp.getString("uuid", "")}\"}"
                                httpConnection.setRequestProperty("Content-Type", "application/json")
                                httpConnection.addRequestProperty("encoding", "UTF-8")
                                httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                                httpConnection.setRequestProperty("accept", "application/json")
                                httpConnection.setRequestProperty("Content-Length", "${json.length}")
                                httpConnection.connectTimeout = 5000
                                httpConnection.readTimeout = 5000
                                var output = httpConnection.outputStream
                                output.write(json.toByteArray())
                                output.flush()
                                output.close()
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
                                    var intent = Intent(baseContext, PayWebActivity::class.java)
                                    Log.e("MainActivity", json.toString())
                                    intent.putExtra("orderInfo", json.toString())
                                    startActivity(intent)
                                    finish()
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
            }
        })
        block_list.adapter = blockAdapter
    }
}