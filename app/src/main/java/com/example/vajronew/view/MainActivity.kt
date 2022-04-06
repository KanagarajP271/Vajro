package com.example.vajronew.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vajronew.MyApplication
import com.example.vajronew.R
import com.example.vajronew.adapter.ProductAdapter
import com.example.vajronew.database.ProductDatabase
import com.example.vajronew.databinding.ActivityMainBinding
import com.example.vajronew.model.Cart
import com.example.vajronew.service.MyViewModelFactory
import com.example.vajronew.service.ProjectRepository
import com.example.vajronew.service.RetrofitService
import com.example.vajronew.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), ProductAdapter.ItemClickListener, View.OnClickListener {

    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private val adapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setValues()
    }

    override fun onAddClicked(id: String?, count: Int?, type: String?) {
        if (!id.equals("")) {
            ProductDatabase.getDatabase(MyApplication.context).Dao().insertCart(Cart(id!!, count))
        }
    }

    private fun setValues() {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = ProjectRepository(retrofitService)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)

        viewModel.productsListLiveData.observe(this) {
            adapter.addData(it, this, this)
            binding.recyclerview.adapter = adapter
            adapter.notifyDataSetChanged()
            for (i in it.indices) {
                Log.d("zzzz", it[i].id.toString())
            }
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this) {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        }

        binding.imgCart.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)

        viewModel.getAllProducts()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.imgBack -> {
                finish()
            }

            R.id.imgCart -> {
                startActivity(Intent(this@MainActivity,CartActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setValues()
    }

}