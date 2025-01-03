package com.example.pharmalink.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmalink.AppDatabase
import com.example.pharmalink.R
import com.example.pharmalink.adapters.MedicineAdapter
import com.example.pharmalink.adapters.VitaminAdapter
import com.example.pharmalink.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var vitaminAdapter: VitaminAdapter
    private lateinit var medicineAdapter: MedicineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.seeAllVitamins.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_vitaminListFragment)
        }
        binding.seeAllMedicines.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_medicineListFragment)
        }
        binding.vitaminButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_vitaminListFragment)
        }
        binding.medicineButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_medicineListFragment)
        }
        binding.cartButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_cart)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Configurer la barre de recherche
        setupSearchBar()
        // Configuration des RecyclerViews
        setupVitaminsRecyclerView()
        setupMedicinesRecyclerView()
    }

    private fun setupVitaminsRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val db = AppDatabase.getDatabase(requireContext())
                val vitamins = db.vitaminDao().getAllVitamins()

                // Mettre à jour l'interface utilisateur sur le thread principal
                withContext(Dispatchers.Main) {
                    vitaminAdapter = VitaminAdapter(vitamins, R.layout.item_vitamin, this@HomeFragment)
                    binding.recyclerViewhorizontal.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = vitaminAdapter
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupMedicinesRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val db = AppDatabase.getDatabase(requireContext())
                val medicines = db.medicineDao().getAllMedicines()

                // Mettre à jour l'interface utilisateur sur le thread principal
                withContext(Dispatchers.Main) {
                    medicineAdapter = MedicineAdapter(medicines, R.layout.item_medicine, this@HomeFragment)
                    binding.medicinesRecyclerViewHori.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = medicineAdapter
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //recherche
    private fun setupSearchBar() {
        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString().trim()
                if (query.isNotEmpty()) {
                    performSearch(query)
                } else {
                    Toast.makeText(requireContext(), "Veuillez saisir un nom de produit", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                false
            }
        }
    }

    private fun performSearch(query: String) {
        val db = AppDatabase.getDatabase(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            val medicine = db.medicineDao().searchMedicineByName("%$query%")
            val vitamin = db.vitaminDao().searchVitaminByName("%$query%")

            withContext(Dispatchers.Main) {
                when {
                    medicine != null -> navigateToDetail(medicine.id, "medicine")
                    vitamin != null -> navigateToDetail(vitamin.id, "vitamin")
                    else -> Toast.makeText(requireContext(), "Produit non trouvé", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToDetail(productId: Int, productType: String) {
        val action = HomeFragmentDirections.actionHomeToDetailFragment(productId, productType)
        findNavController().navigate(action)
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
