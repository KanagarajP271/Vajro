package com.example.vajronew.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vajronew.MyApplication
import com.example.vajronew.R
import com.example.vajronew.adapter.CartAdapter
import com.example.vajronew.database.ProductDatabase
import com.example.vajronew.databinding.ActivityCartBinding
import com.example.vajronew.model.Cart
import com.example.vajronew.model.Product

class CartActivity : AppCompatActivity(), CartAdapter.AddClickListener, View.OnClickListener {

    lateinit var binding: ActivityCartBinding
    private val adapter = CartAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setValues()
    }

    private fun setValues() {
        binding.progressDialog.visibility = View.VISIBLE

        val cartList = ProductDatabase.getDatabase(MyApplication.context).Dao().allCarts
        val list: MutableList<Product> = mutableListOf()

        if (cartList.isNotEmpty()) {
            binding.progressDialog.visibility = View.GONE
            binding.clBottom.visibility = View.VISIBLE
            binding.nsCenter.visibility = View.VISIBLE
            for (i in cartList.indices) {
                val product = ProductDatabase.getDatabase(MyApplication.context).Dao().getProductByID(cartList[i].id)
                product.count = cartList[i].count
                list.add(product)
            }
        }
        else {
            binding.tvErrorMessage.visibility = View.VISIBLE
            binding.progressDialog.visibility = View.GONE
            binding.clBottom.visibility = View.GONE
            binding.nsCenter.visibility = View.GONE
        }

        adapter.addData(list, this, this)
        binding.recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()

        setPriceDetails(list)

        binding.imgBack.setOnClickListener(this)
        binding.tvErrorMessage.setOnClickListener(this)
    }

    private fun setPriceDetails(list: MutableList<Product>) {
        var strSpecialPrice: Double? = 0.0
        var discount: Double? = 0.0
        val totalAmount: Double?
        val saveAmount: Double?
        if (list.isNotEmpty()) {
            if (strSpecialPrice != null) {
                for (i in list.indices) {
                    val specialPrice = list[i].special?.replace("[^\\d.]".toRegex(), "")?.toDouble()
                    val price = list[i].price?.replace("[^\\d.]".toRegex(), "")?.toDouble()
                    if (specialPrice != null) {
                        strSpecialPrice = strSpecialPrice!! + (specialPrice * list[i].count!!)
                    }
                    if (discount != null) {
                        if (specialPrice!! < price!!) {
                            discount += (price * list[i].count!!) - (specialPrice * list[i].count!!)
                        }
                    }
                }
                totalAmount = strSpecialPrice
                saveAmount = discount

                binding.tvPriceLabel.text = "Price (${list.size} items)"
                binding.tvPrice.text = getString(R.string.rupee) + " ${strSpecialPrice.toString()}"
                binding.tvDiscount.text = getString(R.string.rupee) + " ${discount.toString()}"
                binding.tvTotalAmount.text = getString(R.string.rupee) + " ${totalAmount.toString()}"
                if (saveAmount == 0.0) {
                    binding.tvSave.visibility = View.GONE
                }
                else {
                    binding.tvSave.visibility = View.VISIBLE
                    binding.tvSave.text = "You will save" + getString(R.string.rupee) +  " $saveAmount on this order"
                }

                binding.tvBottomPrice.text = getString(R.string.rupee) + " ${strSpecialPrice.toString()}"
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.imgBack -> {
                finish()
            }
            R.id.tvErrorMessage -> {
                finish()
            }
        }
    }

    override fun onAddClicked(id: String?, count: Int?, type: String?) {
        Toast.makeText(this@CartActivity, id.toString()+" "+type+" "+count.toString(), Toast.LENGTH_SHORT).show()

        if (!id.equals("")) {
            if (type.equals("delete")) {
                ProductDatabase.getDatabase(MyApplication.context).Dao().deleteCartByID(id)
                setValues()
            }
            else {
                ProductDatabase.getDatabase(MyApplication.context).Dao().insertCart(Cart(id!!, count))
                setValues()
            }
        }
    }
}