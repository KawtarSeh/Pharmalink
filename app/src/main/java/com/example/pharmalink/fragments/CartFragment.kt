package com.example.pharmalink.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmalink.adapters.CartAdapter
import com.example.pharmalink.databinding.FragmentCartBinding
import com.example.pharmalink.models.Vitamin

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter

    private val cartItems: MutableList<Vitamin> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Exemple de données à afficher dans le panier
        cartItems.addAll(
            listOf(
                Vitamin(1, "Vitamin A", 12.99, "https://via.placeholder.com/150"),
                Vitamin(2, "Vitamin B", 14.99, "https://via.placeholder.com/150"),
                Vitamin(3,"Vitamin B", 14.99, "https://via.placeholder.com/150")
            )
        )
        cartAdapter.notifyDataSetChanged()

        // Bouton Checkout
        binding.checkoutButton.setOnClickListener {
            // Logique pour finaliser la commande
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(cartItems)
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
