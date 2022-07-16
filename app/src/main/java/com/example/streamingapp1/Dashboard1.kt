package com.example.streamingapp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.streamingapp1.databinding.ActivityDashboard1Binding
import com.example.streamingapp1.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.Exception

class Dashboard1 : AppCompatActivity() {
    private lateinit var binding: ActivityDashboard1Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboard1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.navigation_home -> loadFragment(Home())
                R.id.navigation_profile -> loadFragment(Profile())

                else ->{

                }
            }
            true
        }
    }
    private fun loadFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}
