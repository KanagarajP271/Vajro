package com.example.vajronew.model

import androidx.room.*
import com.example.vajronew.service.IntListTypeConverters

data class Root (var products: ArrayList<Product>?)

@Entity(tableName = "product_detail")
@TypeConverters(IntListTypeConverters::class)
data class Product (
    @PrimaryKey
    var id: String = "",
    var name: String? = "",
    var product_id: String? = "",
    var sku: String? = "",
    var image: String? = "",
    var thumb: String? = "",
    var zoom_thumb: String = "",
    @Embedded var options: ArrayList<Any>? = ArrayList(),
    var description: String? = "",
    var href: String? = "",
    var quantity: Int? = 0,
    @Embedded var images: ArrayList<Any>? = ArrayList(),
    var price: String? = "",
    var special: String? = "",
    @Ignore
    var count: Int? = 0
)