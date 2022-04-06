package com.example.vajronew.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vajronew.viewmodel.MainViewModel

class MyViewModelFactory constructor(private val repository: ProjectRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}