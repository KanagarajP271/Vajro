package com.example.vajronew.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.vajronew.service.IntListTypeConverters

@Entity(tableName = "cart_detail")
@TypeConverters(IntListTypeConverters::class)
data class Cart (
    @PrimaryKey
    var id: String = "",
    var count: Int? = 0
)