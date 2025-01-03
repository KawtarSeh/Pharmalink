// Updated MedicineAdapter to follow the same structure as VitaminAdapter
package com.example.pharmalink.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmalink.R
import com.example.pharmalink.databinding.ItemMedicineListBinding
import com.example.pharmalink.fragments.MedicineListFragmentDirections
import com.example.pharmalink.models.Medicine

class MedicineAdapter(
    private val medicineList: List<Medicine>,
    private val layoutId: Int,
    private val fragment: Fragment
) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    inner class MedicineViewHolder(private val binding: ItemMedicineListBinding) : RecyclerView.ViewHolder(binding.root) {

        val detailButton: ImageButton = binding.detailButton

        fun bind(medicine: Medicine) {
            val medicineName: TextView = binding.medicineName
            val medicinePrice: TextView = binding.medicinePrice
            val medicineImage: ImageView = binding.medicineImage

            medicineName.text = medicine.name
            medicinePrice.text = "dh${medicine.price}"
            com.squareup.picasso.Picasso.get().load(medicine.imageUrl).into(medicineImage)

            detailButton.setOnClickListener {
                val action = MedicineListFragmentDirections.actionMedToDetail(
                    productId = medicine.id,
                    productType = "medicine"
                )
                fragment.findNavController().navigate(action)
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(medicineList[position])
    }

    override fun getItemCount(): Int = medicineList.size
}
