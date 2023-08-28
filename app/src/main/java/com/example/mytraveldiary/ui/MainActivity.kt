package com.example.mytraveldiary.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mytraveldiary.R
import com.example.mytraveldiary.databinding.ActivityMainBinding
import com.example.mytraveldiary.ui.auth.SignInActivity
import com.example.mytraveldiary.ui.details.DiaryListAdapter
import com.google.firebase.auth.FirebaseAuth
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

class MainActivity : AppCompatActivity(), DIAware {
    override val di by closestDI()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val adapter = DiaryListAdapter {}
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.materialToolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.logout) {
                signOutUser()
            }
            false
        }

        val searchItem = binding.materialToolbar.menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter(newText)
                return true
            }
        })

    }

    private fun signOutUser() {
        try {
            firebaseAuth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(this, "Error signing out", Toast.LENGTH_SHORT).show()
        }
    }
}
