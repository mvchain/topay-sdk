package com.mvc.to_pay_sdk.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.mvc.to_pay_sdk.R
import com.mvc.to_pay_sdk.bean.BlockBean
import com.mvc.to_pay_sdk.listener.BlockOnClickListener
import java.text.SimpleDateFormat
import java.util.*

class BlockAdapter(private val context: Context, private val layoutRes: Int, private val data: ArrayList<BlockBean>) : RecyclerView.Adapter<BlockAdapter.BlockViewHolder>() {
    private lateinit var blockListener: BlockOnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        return BlockViewHolder(LayoutInflater.from(context).inflate(layoutRes, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setBlockOnClickListener(blockListener: BlockOnClickListener) {
        this.blockListener = blockListener
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        holder.orderLayout.setOnClickListener {
            blockListener.onClick(holder.orderLayout, position)
        }
//        holder.orderLayout.text = "${data[position].amount}"
        holder.orderLayout.setBackgroundResource(data[position].imageRes)
    }

    class BlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderLayout = itemView.findViewById<Button>(R.id.coins_layout)
    }
}