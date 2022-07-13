package com.example.streamingapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.streamingapp1.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class Dashboard : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    private lateinit var binding: ActivityDashboardBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        initData();
    }

    private fun initData(){
        auth = FirebaseAuth.getInstance();
        // auth.signOut()
        checkifuserloggedin();
    }

    private fun checkifuserloggedin(){
        val currentUser = auth.currentUser;

        if(currentUser != null){
            startActivity(Intent(this,Dashboard::class.java))
            finish()
        }else{
            startActivity(Intent(this,Login::class.java))
            finish()
        }
    }
}