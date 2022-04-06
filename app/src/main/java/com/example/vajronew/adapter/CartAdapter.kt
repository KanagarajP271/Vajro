package com.example.vajronew.adapter

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vajronew.R
import com.example.vajronew.model.Product

class CartAdapter(): RecyclerView.Adapter<CartAdapter.AdapterVH>() {

    var productsList: List<Product> = ArrayList()
    lateinit var context: Context
    lateinit var addClickListener: AddClickListener

    fun addData(list: List<Product>, context: Context, addClickListener: AddClickListener) {
        productsList = list
        this.context = context
        this.addClickListener = addClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterVH {
        return AdapterVH(LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: CartAdapter.AdapterVH, position: Int) {
        val product = productsList[position]

        holder.tvName.text = product.name

        var strPrice = product.price
        var strSpecialPrice = product.special
        strPrice = strPrice?.replace("[^\\d.]".toRegex(), "")
        strSpecialPrice = strSpecialPrice?.replace("[^\\d.]".toRegex(), "")

        holder.tvSpecialPrice.text = context.getString(R.string.rupee) + (strSpecialPrice?.toDouble()!! * product.count!!).toString()
        holder.tvPrice.text = context.getString(R.string.rupee) + (strPrice?.toDouble()!! * product.count!!).toString()
        holder.tvPrice.paintFlags = holder.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        if (strSpecialPrice.toDouble() < strPrice.toDouble()) {
            holder.tvPrice.visibility = View.VISIBLE
        }
        else {
            holder.tvPrice.visibility = View.GONE
        }

        Log.d("aaaaa", "$strPrice $strSpecialPrice")

        Glide.with(context)
            .load(product.image)
            .placeholder(R.color.placeholder)
            .into(holder.imgPerson)

        holder.tvCount.text = product.count.toString()

        var count = product.count
        holder.tvMinus.setOnClickListener {
            if (count!! > 1) {
                count = count!! - 1
                holder.tvCount.text = count.toString()
                addClickListener.onAddClicked(product.id, count, "minus")
            }
        }

        holder.tvPlus.setOnClickListener {
            count = count!! + 1
            holder.tvCount.text = count.toString()
            addClickListener.onAddClicked(product.id, count, "plus")
        }

        holder.tvRemove.setOnClickListener {
            addClickListener.onAddClicked(product.id, 0, "delete")
        }
    }

    override fun getItemCount() = productsList.size

    inner class AdapterVH(view: View): RecyclerView.ViewHolder(view) {
        val imgPerson: ImageView = view.findViewById(R.id.imgPerson)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvSpecialPrice: TextView = view.findViewById(R.id.tvSpecialPrice)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val cdView: CardView = view.findViewById(R.id.cdView)
        val tvMinus: TextView = view.findViewById(R.id.tvMinus)
        val tvCount: TextView = view.findViewById(R.id.tvCount)
        val tvPlus: TextView = view.findViewById(R.id.tvPlus)
        val tvRemove: TextView = view.findViewById(R.id.tvRemove)

    }

    interface AddClickListener {
        fun onAddClicked(id: String?, count: Int?, type: String?)
    }
}