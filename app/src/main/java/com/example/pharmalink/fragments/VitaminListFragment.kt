package com.example.pharmalink.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmalink.AppDatabase
import com.example.pharmalink.R
import com.example.pharmalink.adapters.VitaminAdapter
import com.example.pharmalink.databinding.FragmentVitaminListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val db = AppDatabase.getDatabase(requireContext())
                val vitamins = db.vitaminDao().getAllVitamins()

                withContext(Dispatchers.Main) {
                    vitaminAdapter = VitaminAdapter(vitamins, R.layout.item_vitamin_list, this@VitaminListFragment)
                    binding.recyclerViewverticalvit.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        adapter = vitaminAdapter
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}