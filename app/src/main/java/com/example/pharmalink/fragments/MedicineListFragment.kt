package com.example.pharmalink.ui.medicine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmalink.R
import com.example.pharmalink.adapters.MedicineAdapter
import com.example.pharmalink.databinding.FragmentMedicineListBinding
import com.example.pharmalink.models.Medicine

class MedicineListFragment : Fragment() {

    private var _binding: FragmentMedicineListBinding? = null
    private val binding get() = _binding!!

    private lateinit var medicineAdapter: MedicineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicineListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuration du RecyclerView pour un défilement vertical
        setupMedicineRecyclerView()
    }

    private fun setupMedicineRecyclerView() {
        medicineAdapter = MedicineAdapter(getDummyVitamins(), R.layout.item_medicine_list, this)
        binding.recyclerViewVertical.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = medicineAdapter
        }
    }



    private fun getDummyVitamins(): List<Medicine> {
        return listOf(
            Medicine(1, "Medicine A", 12.99, "https://via.placeholder.com/150"),
            Medicine(2, "Medicine B", 14.99, "https://via.placeholder.com/150"),
            Medicine(3, "Medicine C", 9.99, "https://via.placeholder.com/150"),
            Medicine(5, "Medicine ", 9.99, "https://via.placeholder.com/150",),
            Medicine(6, "Medicine C", 8.49, "https://via.placeholder.com/150",),
            Medicine(7, " Medicine", 9.99, "https://via.placeholder.com/150",),
            Medicine(8, "Medicine", 9.99, "https://via.placeholder.com/150",),
            Medicine(9, "Medicine K", 10.49, "https://via.placeholder.com/150",)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

