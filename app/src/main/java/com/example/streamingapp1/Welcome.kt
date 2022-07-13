package com.example.streamingapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager

class Welcome : AppCompatActivity() {
    //deklarasi view pager dan my adapter
    var viewPager: ViewPager? = null
    var adapter: MyAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val button = findViewById<Button>(R.id.buttonsignin)
        val button1 = findViewById<Button>(R.id.buttonsignup)
        button.setOnClickListener { v: View? -> openLoginActivity() }
        button1.setOnClickListener { v: View? -> openRegisterActivity() }
        //memanggil view pager dan my adapter
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        adapter = MyAdapter(this)
        viewPager.setAdapter(adapter)
    }

    fun openLoginActivity() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
    fun openRegisterActivity() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
}