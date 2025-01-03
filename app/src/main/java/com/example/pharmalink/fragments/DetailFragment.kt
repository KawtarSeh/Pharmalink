package com.example.pharmalink.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.pharmalink.AppDatabase
import com.example.pharmalink.R
import com.example.pharmalink.SessionManager
import com.example.pharmalink.databinding.FragmentDetailBinding
import com.example.pharmalink.models.CartItem
import com.example.pharmalink.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private var quantity = 1
    private var basePrice = 0.0 // Prix unitaire du produit
    private var selectedProduct: Product? = null // Référence au produit actuel
    val userId = SessionManager.getUserId()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val productId = args.productId
        val productType = args.productType

        loadProductDetails(productId, productType)

        // Gestion des boutons d'incrémentation et de décrémentation
        binding.increaseQuantity.setOnClickListener {
            quantity++
            updateQuantityAndPrice()
        }

        binding.decreaseQuantity.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantityAndPrice()
            }
        }

        // Ajout au panier
        binding.buyNowButton.setOnClickListener {
            selectedProduct?.let {
                addToCart(it)
            } ?: run {
                Toast.makeText(requireContext(), "Produit non chargé", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Mise à jour de la quantité et du prix total
    private fun updateQuantityAndPrice() {
        binding.quantityValue.text = quantity.toString()
        val totalPrice = String.format("%.2f", quantity * basePrice)
        binding.productPrice.text = "Dh$totalPrice"
    }

    // Chargement des détails du produit
    private fun loadProductDetails(productId: Int, productType: String) {
        val db = AppDatabase.getDatabase(requireContext())

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val product = if (productType == "medicine") {
                    db.medicineDao().getMedicineById(productId)
                } else {
                    db.vitaminDao().getVitaminById(productId)
                }

                withContext(Dispatchers.Main) {
                    product?.let {
                        selectedProduct = it // Stocke le produit sélectionné
                        binding.productName.text = it.name
                        binding.productDescription.text = "Details : ${it.description}..."
                        binding.productImage.setImageResource(it.imageUrl)
                        basePrice = it.price // Initialisation du prix unitaire
                        updateQuantityAndPrice() // Mise à jour de l'interface utilisateur
                    } ?: run {
                        binding.productName.text = getString(R.string.product_not_found)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.productName.text = getString(R.string.error_loading_product)
                }
            }
        }
    }

    // Ajout au panier
    private fun addToCart(product: Product) {
        lifecycleScope.launch(Dispatchers.IO) {

            val userId = SessionManager.getUserId() // Récupère l'userId actuel
            println("Ajout au panier pour userId : $userId") // Log de vérification
            val db = AppDatabase.getDatabase(requireContext())
            val existingItem = db.cartDao().getCartItemByUserAndProduct(userId, product.id)

            if (existingItem != null) {
                println("Produit existant : ${existingItem.name}, Quantité : ${existingItem.quantity}")
                existingItem.quantity += 1
                db.cartDao().updateCartItem(existingItem)
            } else {
                println("Ajout d'un nouveau produit : ${product.name}")
                db.cartDao().insertCartItem(
                    CartItem(
                        userId = userId,
                        productId = product.id,
                        name = product.name,
                        price = product.price,
                        imageResId = product.imageUrl,
                        quantity = 1
                    )
                )
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(),
                    "${product.name} ajouté au panier (quantité : $quantity)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
