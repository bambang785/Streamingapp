package com.example.streamingapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.streamingapp1.databinding.ActivityDashboard1Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class Dashboard1 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityDashboard1Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboard1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val homefragment = Home()

        makeCurrentFragment (homefragment)

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.navigation_home -> makeCurrentFragment(homefragment)
            }
            true
        }
        auth = FirebaseAuth.getInstance()
        checkUser()
    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, Welcome::class.java))
            finish()
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_fragment, fragment)
            commit()
        }

    }
}