package com.example.pharmalink.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmalink.R
import com.example.pharmalink.databinding.FragmentVitaminListBinding
import com.example.pharmalink.databinding.ItemVitaminListBinding
import com.example.pharmalink.models.Vitamin
import com.example.pharmalink.ui.vitamins.VitaminListFragment
import com.squareup.picasso.Picasso

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
                // Set the click listener for the detail button
            detailButton.setOnClickListener {
                // Use the fragment's NavController to navigate
                fragment.findNavController().navigate(R.id.action_vit_to_detail)
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
