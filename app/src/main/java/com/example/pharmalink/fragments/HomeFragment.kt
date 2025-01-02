package com.example.pharmalink.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmalink.R
import com.example.pharmalink.adapters.MedicineAdapter
import com.example.pharmalink.adapters.VitaminAdapter
import com.example.pharmalink.databinding.FragmentHomeBinding
import com.example.pharmalink.models.Medicine
import com.example.pharmalink.models.Vitamin

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
        binding.seeAllVitamins.setOnClickListener{
            findNavController().navigate(R.id.action_home_to_vitaminListFragment)
        }
        binding.seeAllMedicines.setOnClickListener{
            findNavController().navigate(R.id.action_home_to_medicineListFragment)
        }
        binding.vitaminButton.setOnClickListener{
            findNavController().navigate(R.id.action_home_to_medicineListFragment)
        }
        binding.medicineButton.setOnClickListener{
            findNavController().navigate(R.id.action_home_to_medicineListFragment)
        }
        binding.cartButton.setOnClickListener{
            findNavController().navigate(R.id.action_home_to_cart)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuration des RecyclerViews
        setupVitaminsRecyclerView()
        setupMedicinesRecyclerView()





    }

    private fun setupVitaminsRecyclerView() {
        vitaminAdapter = VitaminAdapter(getDummyVitamins(), R.layout.item_vitamin,this)
        binding.recyclerViewHorizontal.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = vitaminAdapter
        }
    }



    // Initialisation et configuration du RecyclerView pour les vitamines
    private fun setupMedicinesRecyclerView() {
        medicineAdapter = MedicineAdapter(getDummyMedicines(),R.layout.item_medicine,this)
        binding.medicinesRecyclerViewHori.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = medicineAdapter
        }
    }



    private fun getDummyVitamins(): List<Vitamin> {
        return listOf(
            Vitamin(1, "Vitamin C", 12.99, "https://via.placeholder.com/150"),
            Vitamin(2, "Vitamin D", 15.49, "https://via.placeholder.com/150"),
            Vitamin(3, "Vitamin B12", 9.99, "https://via.placeholder.com/150"),
            Vitamin(3, "Vitamin B12", 9.99, "https://via.placeholder.com/150")
        )
    }

    private fun getDummyMedicines(): List<Medicine> {
        return listOf(
            Medicine(1, "Paracetamol", 4.99, "https://via.placeholder.com/150"),
            Medicine(2, "Ibuprofen", 5.49, "https://via.placeholder.com/150"),
            Medicine(3, "Aspirin", 3.99, "https://via.placeholder.com/150"),
            Medicine(3, "Aspirin", 3.99, "https://via.placeholder.com/150")
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
