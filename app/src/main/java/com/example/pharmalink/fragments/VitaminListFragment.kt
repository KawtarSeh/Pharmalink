package com.example.pharmalink.ui.vitamins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmalink.R
import com.example.pharmalink.adapters.VitaminAdapter
import com.example.pharmalink.databinding.FragmentVitaminListBinding
import com.example.pharmalink.models.Vitamin

class VitaminListFragment : Fragment() {

    private var _binding: FragmentVitaminListBinding? = null
    private val binding get() = _binding!!

    private lateinit var vitaminAdapter: VitaminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVitaminListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuration du RecyclerView pour un défilement vertical
        setupVitaminsRecyclerView()
    }

    private fun setupVitaminsRecyclerView() {
        vitaminAdapter = VitaminAdapter(getDummyVitamins(), R.layout.item_vitamin_list, this)
        binding.recyclerViewVertical.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = vitaminAdapter
        }
    }



    private fun getDummyVitamins(): List<Vitamin> {
        return listOf(
            Vitamin(1, "Vitamin A", 12.99, "https://via.placeholder.com/150"),
            Vitamin(2, "Vitamin B", 14.99, "https://via.placeholder.com/150"),
            Vitamin(3, "Vitamin C", 9.99, "https://via.placeholder.com/150"),
            Vitamin(5, "Calcium & Magnesium", 9.99, "https://via.placeholder.com/150",),
            Vitamin(6, "Vitamin C", 8.49, "https://via.placeholder.com/150",),
            Vitamin(7, "Calcium & Magnesium", 9.99, "https://via.placeholder.com/150",),
            Vitamin(8, "Calcium & Magnesium", 9.99, "https://via.placeholder.com/150",),
            Vitamin(9, "Vitamin K", 10.49, "https://via.placeholder.com/150",)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

