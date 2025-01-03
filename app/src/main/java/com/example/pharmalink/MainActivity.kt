package com.example.pharmalink

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.pharmalink.databinding.ActivityMainBinding
import com.example.pharmalink.models.Medicine
import com.example.pharmalink.models.Vitamin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Activer DataBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurer la Toolbar comme ActionBar
        setSupportActionBar(binding.toolbar)

        enableEdgeToEdge()

        // Configurer la gestion des barres système
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialiser SessionManager
        SessionManager.init(this)

        // Configurer le NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        // Configurer AppBar avec le DrawerLayout
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        // Vérifier l'état de connexion
        if (SessionManager.isLoggedIn()) {
            navController.navigate(R.id.home)
        } else {
            navController.navigate(R.id.welcome_screen)
        }

        // Configurer les clics sur le menu
        setupDrawerMenu()

        // Insertion des données initiales
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(applicationContext)

            // Ajouter des médicaments
            db.medicineDao()
                .insertMedicine(Medicine(1, "Paracétamol", 4.99, R.drawable.calcium, "Utilisé pour soulager la douleur et réduire la fièvre.",1))
            db.medicineDao()
                .insertMedicine(Medicine(2, "Ibuprofen", 5.49, R.drawable.omega3,"Médicament anti-inflammatoire non stéroïdien pour soulager la douleur",1))

            // Ajouter des vitamines
            db.vitaminDao()
                .insertVitamin(Vitamin(3, "Vitamin C", 12.99, R.drawable.vitamind3,"Renforce le système immunitaire et protège les cellules",2))
            db.vitaminDao()
                .insertVitamin(Vitamin(4, "Vitamin D", 15.49, R.drawable.vitamind3,"Aide à réguler le calcium et à maintenir la santé des os.",1))

            withContext(Dispatchers.Main) {
                // Si besoin, mettez à jour l'interface utilisateur
            }
        }
    }

    private fun setupDrawerMenu() {
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.home)
                }
                R.id.profile -> {
                    navController.navigate(R.id.profile)
                }
                R.id.action_home_to_cart-> {
                    navController.navigate(R.id.fragmentcart)
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}
