package com.example.streamingapp1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.streamingapp1.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private var mIsShowPass = false
    private lateinit var binding: ActivityLoginBinding;
    lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root;
        setContentView(view)
        initdata();

        val button = findViewById<ImageButton>(R.id.backbutton)
        button.setOnClickListener { v: View? -> backwelcomeActivity() }


        IvShowHidePass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }

    }

    private fun  initdata(){
        auth = FirebaseAuth.getInstance();
        clickListener()
    }

    private fun  clickListener(){
        binding.signupbutton1.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
            finish()
        }

        binding.buttonsignin1.setOnClickListener {
            getUserData()
        }
    }

    private fun authUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                checkResult(it.isSuccessful)
            }
    }


    private fun getUserData(){
        var email = binding.txUsername.text.toString();
        var password = binding.etPass.text.toString();
        if(email.isNotEmpty() && password.isNotEmpty()){

            //auth user
            authUser(email, password)
        }else{
            Toast.makeText(this,"Isi semua data", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkResult(isSuccess: Boolean){
        if(isSuccess){
            startActivity(Intent(this,Dashboard1::class.java))
            finish()
        }else{
            Toast.makeText(this,"Login gagal", Toast.LENGTH_LONG).show()
        }
    }

    private fun showPassword(isShow: Boolean){
        if (isShow) {
            etPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            IvShowHidePass.setImageResource(R.drawable.ic_hide_password_24)
        }   else {
            etPass.transformationMethod = PasswordTransformationMethod.getInstance()
            IvShowHidePass.setImageResource(R.drawable.ic_show_password_24)
        }
        etPass.setSelection(etPass.text.toString().length)
    }
    fun backwelcomeActivity() {
        onBackPressed()
    }
}