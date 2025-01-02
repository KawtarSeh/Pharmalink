package com.example.pharmalink.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmalink.R
import com.example.pharmalink.models.Vitamin
import com.squareup.picasso.Picasso

class CartAdapter(private val cartItems: List<Vitamin>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.cartItemImage)
        val productName: TextView = view.findViewById(R.id.cartItemName)
        val productPrice: TextView = view.findViewById(R.id.cartItemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.productName.text = item.name
        holder.productPrice.text = "$${item.price}"
        Picasso.get().load(item.imageUrl).into(holder.productImage)
    }

    override fun getItemCount(): Int = cartItems.size
}
