package com.mvc.to_pay_sdk.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mvc.to_pay_sdk.R
import com.mvc.to_pay_sdk.bean.BlockBean
import com.mvc.to_pay_sdk.bean.OrderBean
import com.mvc.to_pay_sdk.listener.BlockOnClickListener
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(private val context: Context, private val layoutRes: Int, private val data: ArrayList<OrderBean>) : RecyclerView.Adapter<OrderAdapter.BlockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        return BlockViewHolder(LayoutInflater.from(context).inflate(layoutRes, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {

    }

    class BlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}