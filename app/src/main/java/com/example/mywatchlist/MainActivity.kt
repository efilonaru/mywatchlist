package com.example.mywatchlist

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mywatchlist.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val navControllerDestination = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.fragment_home -> Log.d("Navigation", "Home selected")
            R.id.fragment_favorite -> Log.d("Navigation", "Search selected")
            R.id.fragment_about -> Log.d("Navigation", "Profile selected")
            R.id.fragment_details -> Log.d("Navigation", "Details selected")
            else -> Log.d("Navigation", "Unknown destination: ${destination.id}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavbar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(navControllerDestination)
    }
}
