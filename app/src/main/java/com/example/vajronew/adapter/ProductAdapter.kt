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

class ProductAdapter(): RecyclerView.Adapter<ProductAdapter.AdapterVH>() {

    var productsList: List<Product> = ArrayList()
    lateinit var context: Context
    lateinit var itemClickListener: ItemClickListener

    fun addData(list: List<Product>, context: Context, itemClickListener: ItemClickListener) {
        productsList = list
        this.context = context
        this.itemClickListener = itemClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterVH {
        return AdapterVH(LayoutInflater.from(parent.context)
            .inflate(R.layout.product_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProductAdapter.AdapterVH, position: Int) {
        val product = productsList[position]

        holder.tvName.text = product.name
        holder.tvSpecialPrice.text = product.special
        holder.tvPrice.text = product.price
        holder.tvPrice.paintFlags = holder.tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        var strPrice = product.price
        var strSpecialPrice = product.special
        strPrice = strPrice?.replace("[^\\d.]".toRegex(), "")
        strSpecialPrice = strSpecialPrice?.replace("[^\\d.]".toRegex(), "")

        if (strSpecialPrice?.toFloat()!! < strPrice?.toFloat()!!) {
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

        /*holder.cdView.setOnClickListener {
            itemClickListener.onItemClick(product.id)
        }*/

        holder.tvCount.text = 0.toString()

        var count: Int = 0
        holder.tvMinus.setOnClickListener {
            if (count > 0) {
                count -= 1
                holder.tvCount.text = count.toString()
                itemClickListener.onAddClicked(product.id, count, "minus")
            }
        }

        holder.tvPlus.setOnClickListener {
            count += 1
            holder.tvCount.text = count.toString()
            itemClickListener.onAddClicked(product.id, count, "plus")
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

    }
    
    interface ItemClickListener {
        fun onAddClicked(id: String?, count: Int?, type: String?)
    }
}