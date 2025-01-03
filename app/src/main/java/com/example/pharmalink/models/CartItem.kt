package com.example.pharmalink.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int, // ID du produit (médicament ou vitamine)
    val userId: Int, // ID de l'utilisateur
    val name: String, // Nom du produit
    val price: Double, // Prix unitaire
    val imageResId: Int, // ID de l'image
    var quantity: Int = 1 // Quantité initiale (modifiable)
)
