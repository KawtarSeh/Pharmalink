package com.example.pharmalink.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitamins")
data class Vitamin(
    @PrimaryKey override val id: Int,
    override val name: String,
    override val price: Double,
    override val imageUrl: Int,
    override val description: String, // Nouveau champ
    override var quantity: Int
): Product
