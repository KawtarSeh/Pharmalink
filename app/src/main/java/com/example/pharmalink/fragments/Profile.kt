package com.example.pharmalink.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmalink.R
import com.example.pharmalink.SessionManager
import com.example.pharmalink.databinding.FragmentProfileBinding

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Charger les informations utilisateur depuis SessionManager
        val fullName = SessionManager.getFullName() ?: "Nom inconnu"
        val userEmail = SessionManager.getUserEmail() ?: "Email inconnu"

        // Afficher les informations dans la vue
        binding.profileName.text = fullName
        binding.profileEmail.text = userEmail

        // Gestion du bouton de déconnexion
        binding.logoutButton.setOnClickListener {
            // Effacer la session utilisateur
            SessionManager.logout()
            // Rediriger vers l'écran de connexion
            findNavController().navigate(R.id.action_profile_to_login)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
