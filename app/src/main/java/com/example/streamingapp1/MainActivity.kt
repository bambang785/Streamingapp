package com.example.streamingapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//Menghilangkan Action bar
        supportActionBar?.hide()
//memindahkan splashscreen ke activity selanjutnya
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, Welcome::class.java )
            startActivity(intent)
            finish()
        }, 3000)
    }
}