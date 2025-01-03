package com.example.pharmalink.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmalink.databinding.ItemCartBinding
import com.example.pharmalink.models.CartItem
import com.squareup.picasso.Picasso

class CartAdapter(
    private val cartItems: MutableList<CartItem>,

    private val onQuantityChanged: (CartItem) -> Unit,
    private val onDeleteItem: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bind(item: CartItem) {
            val itemName: TextView = binding.cartItemName
            val itemPrice: TextView = binding.cartItemPrice
            val itemImage: ImageView = binding.cartItemImage
            val quantity: TextView = binding.quantity

            // Afficher les données de l'article
            itemName.text = item.name
            itemPrice.text = "Dh${item.price * item.quantity}"
            quantity.text = item.quantity.toString()
            Picasso.get().load(item.imageResId).into(itemImage)

            // Gérer les boutons d'incrémentation et de décrémentation
            binding.incrementButton.setOnClickListener {
                item.quantity++
                onQuantityChanged(item)
            }

            binding.decrementButton.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    onQuantityChanged(item)
                }
            }

            // Bouton pour supprimer un article
            binding.deleteitem.setOnClickListener {
                onDeleteItem(item)
            }



            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int {
        println("Nombre d'articles dans l'adaptateur : ${cartItems.size}")
        return cartItems.size
    }
}
