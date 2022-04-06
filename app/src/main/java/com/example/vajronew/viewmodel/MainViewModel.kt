package com.example.vajronew.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vajronew.MyApplication
import com.example.vajronew.database.ProductDatabase
import com.example.vajronew.model.Product
import com.example.vajronew.service.ProjectRepository
import kotlinx.coroutines.*

class MainViewModel constructor(private val projectRepository: ProjectRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val productsListLiveData = MutableLiveData<List<Product>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllProducts() {

        val employeeList = ProductDatabase.getDatabase(MyApplication.context).Dao().allProducts

        if (employeeList.isNotEmpty()) {
            productsListLiveData.value = employeeList
            loading.value = false
        }
        else {
            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                loading.postValue(true)
                val response = projectRepository.getAllProducts()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        ProductDatabase.getDatabase(MyApplication.context).Dao().insertAllProducts(response.body()?.products)
                        productsListLiveData.postValue(response.body()?.products)
                        loading.value = false
                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}