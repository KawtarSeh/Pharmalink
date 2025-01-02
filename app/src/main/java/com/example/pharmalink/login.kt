package com.example.pharmalink

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pharmalink.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [login.newInstance] factory method to
 * create an instance of this fragment.
 */
class login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) { // Utilise Dispatchers.IO pour les opérations de base de données
                val user = db.userDao().getUserByEmailAndPassword(email, password)

                // Retournez sur le thread principal pour mettre à jour l'UI
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        val sharedPreferences = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putBoolean("isLoggedIn", true)
                            putString("userEmail", user.email)
                            putString("fullName", user.fullName)
                            apply()
                        }
                        findNavController().navigate(R.id.action_login_to_profile)
                    } else {
                        Toast.makeText(requireContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Retourner la vue racine du binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
