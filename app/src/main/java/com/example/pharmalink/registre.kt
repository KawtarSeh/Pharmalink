package com.example.pharmalink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pharmalink.databinding.FragmentRegistreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [registre.newInstance] factory method to
 * create an instance of this fragment.
 */
class registre : Fragment() {

    private lateinit var binding: FragmentRegistreBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistreBinding.inflate(inflater, container, false)

        // Configurer le bouton d'inscription
        binding.signupButton.setOnClickListener {
            val fullName = binding.fullNameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmPasswordInput.text.toString()

            // Vérifications de base
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Vérification des doublons dans la base de données
            lifecycleScope.launch(Dispatchers.IO) {
                val existingUser = db.userDao().getUserByEmail(email)

                withContext(Dispatchers.Main) {
                    if (existingUser != null) {
                        Toast.makeText(requireContext(), "Un compte avec cet email existe déjà", Toast.LENGTH_SHORT).show()
                    } else {
                        // Insérer l'utilisateur dans la base de données
                        val user = User(fullName = fullName, email = email, password = password)
                        lifecycleScope.launch(Dispatchers.IO) {
                            db.userDao().insertUser(user)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Votre inscription a été effectuée avec succès", Toast.LENGTH_SHORT).show()
                                // Redirection vers la page de login
                                findNavController().navigate(R.id.action_registre_to_login)
                            }
                        }
                    }
                }
            }
        }

        // Retourner la vue liée
        return binding.root
    }
}
