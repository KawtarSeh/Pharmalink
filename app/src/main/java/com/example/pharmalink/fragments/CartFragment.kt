package com.example.pharmalink.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmalink.AppDatabase
import com.example.pharmalink.R
import com.example.pharmalink.SessionManager
import com.example.pharmalink.adapters.CartAdapter
import com.example.pharmalink.databinding.FragmentCartBinding
import com.example.pharmalink.models.CartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private val cartItems: MutableList<CartItem> = mutableListOf()
    private val userId = SessionManager.getUserId() // Récupère l'ID de l'utilisateur connecté

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadCartItems()

        // Bouton pour finaliser la commande
        binding.checkoutButton.setOnClickListener {
            if (cartItems.isEmpty()) {
                Toast.makeText(requireContext(), "Votre panier est vide", Toast.LENGTH_SHORT).show()
            } else {
                // Logique pour finaliser la commande
                Toast.makeText(requireContext(), "Commande finalisée", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            cartItems,
            onQuantityChanged = { updateCartItem(it) },
            onDeleteItem = { deleteCartItem(it) }
        )
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    private fun loadCartItems() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val db = AppDatabase.getDatabase(requireContext())
                val userId = SessionManager.getUserId() // Récupère l'userId actuel
                val items = db.cartDao().getCartItemsForUser(userId)

                withContext(Dispatchers.Main) {
                    cartItems.clear()
                    cartItems.addAll(items)
                    cartItems.forEach { println("Article : ${it.name}, Quantité : ${it.quantity}") }

                    cartAdapter.notifyDataSetChanged()
                    updateTotalPrice()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Erreur lors du chargement du panier", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateCartItem(item: CartItem) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                AppDatabase.getDatabase(requireContext()).cartDao().updateCartItem(item)
                withContext(Dispatchers.Main) {
                    updateTotalPrice()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteCartItem(item: CartItem) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                AppDatabase.getDatabase(requireContext()).cartDao().deleteCartItem(userId,item.id)
                withContext(Dispatchers.Main) {
                    cartItems.remove(item)
                    cartAdapter.notifyDataSetChanged()
                    updateTotalPrice()
                    Toast.makeText(
                        requireContext(),
                        "${item.name} a été supprimé du panier",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Erreur lors de la suppression", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateTotalPrice() {
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        binding.totalPrice.text = "Dh$totalPrice"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
