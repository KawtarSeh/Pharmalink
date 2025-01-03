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
import com.example.pharmalink.databinding.ItemVitaminListBinding
import com.example.pharmalink.fragments.VitaminListFragmentDirections
import com.example.pharmalink.models.Vitamin


class VitaminAdapter(
    private val vitaminList: List<Vitamin>,
    private val layoutId: Int,
    private val fragment: Fragment // Pass the parent fragment
) : RecyclerView.Adapter<VitaminAdapter.VitaminViewHolder>() {

    inner class VitaminViewHolder(private val binding: ItemVitaminListBinding) : RecyclerView.ViewHolder(binding.root) {

        val detailButton: ImageButton = binding.detailButton

        fun bind(vitamin: Vitamin) {
            // Set any other data binding for vitamin if needed
            val vitaminName: TextView = binding.vitaminName
            val vitaminPrice: TextView = binding.vitaminPrice
            val vitaminImage: ImageView = binding.vitaminImage

            vitaminName.text = vitamin.name
            vitaminPrice.text = "dh${vitamin.price}"
            com.squareup.picasso.Picasso.get().load(vitamin.imageUrl).into(vitaminImage)
                // Set the click listener for the detail button

            detailButton.setOnClickListener {
                val action = VitaminListFragmentDirections.actionVitToDetail(
                    productId = vitamin.id,
                    productType = "vitamin"
                )
                fragment.findNavController().navigate(action)
            }

            binding.executePendingBindings()  // Ensure data binding updates are immediate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VitaminViewHolder {
        val binding = ItemVitaminListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VitaminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VitaminViewHolder, position: Int) {
        holder.bind(vitaminList[position])
    }

    override fun getItemCount(): Int = vitaminList.size
}
