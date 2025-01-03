package com.example.pharmalink.models

interface Product {
    val id : Int
    val name: String
    val price: Double
    val imageUrl: Int
    val description: String // Nouveau champ
    var quantity: Int
}
