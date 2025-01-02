package com.example.pharmalink.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmalink.R
import com.example.pharmalink.databinding.FragmentWelcomeScreenBinding;


class Welcome_screen : Fragment() {
    private var _binding: FragmentWelcomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWelcomeScreenBinding.inflate(inflater, container, false)

        // Configuration du clic sur le bouton en utilisant le binding
        binding.joinButton.setOnClickListener {
            // Utilisation de findNavController() pour naviguer
            findNavController().navigate(R.id.action_welcome_to_register)
        }
        binding.loginLinkWelcomme.setOnClickListener {
            // Utilisation de findNavController() pour naviguer
            findNavController().navigate(R.id.action_welcome_to_login)
        }


        // Retourner la vue racine du binding
        return binding.getRoot();



    }


}