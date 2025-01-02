package com.example.pharmalink

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pharmalink.SessionManager // Assurez-vous que SessionManager est correctement importé

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configurer la gestion des barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialiser SessionManager
        SessionManager.init(this)

        // Obtenir le NavController depuis le NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        // Vérifier l'état de connexion et rediriger l'utilisateur
        if (SessionManager.isLoggedIn()) {
            // Si l'utilisateur est connecté, rediriger vers le profil
            navController.navigate(R.id.profile)
        } else {
            // Sinon, rester sur l'écran de bienvenue
            navController.navigate(R.id.welcome_screen)
        }
    }
}
