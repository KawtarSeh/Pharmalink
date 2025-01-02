package com.example.pharmalink

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pharmalink.databinding.FragmentProfileBinding


class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Charger les informations utilisateur depuis SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val fullName = sharedPreferences.getString("fullName", "Nom inconnu")
        val userEmail = sharedPreferences.getString("userEmail", "Email inconnu")

        // Afficher les informations dans la vue
        binding.profileName.text = fullName
        binding.profileEmail.text = userEmail

        binding.logoutButton.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear() // Supprime toutes les données de session
            editor.apply()

            // Rediriger vers l'écran de connexion
            findNavController().navigate(R.id.action_profile_to_login)
        }

        return binding.root
    }


}