package com.example.vajronew.service

class ProjectRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllProducts() = retrofitService.getAllProducts()
}