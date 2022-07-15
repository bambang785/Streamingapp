package com.example.streamingapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.example.streamingapp1.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    private lateinit var binding: ActivityRegisterBinding;
    private var mIsShowPass = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initData();
        val button = findViewById<ImageButton>(R.id.backbutton1)
        button.setOnClickListener { v: View? -> backwelcomeActivity() }

        IvShowHidePass1.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }
    }
    private fun  initData(){
        auth = FirebaseAuth.getInstance();
        clickListener();
    }

    private fun clickListener(){
        binding.buttonsigninregist.setOnClickListener {
            createUser()
        }
        binding.signupbuttonregist.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
            finish()
        }
    }
    private fun createUser() {
        var email = binding.txusernameRegist.text.toString()
        var password = binding.txpasswordRegist.text.toString()
        var cpassword = binding.txpasswordconfRegist.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty()) {
            if (password == cpassword) {
                saveUser(email, password)
            }
        }
    }
    private fun saveUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{

                checkResults(it.isSuccessful)
            }
    }

    private fun  checkResults(isSuccess: Boolean){
        if (isSuccess){
            startActivity(Intent(this,Login::class.java))
            Toast.makeText(this,"Daftar Berhasil", Toast.LENGTH_LONG).show()
            finish()
        }else{
            Toast.makeText(this@Register, "Daftar Gagal", Toast.LENGTH_LONG).show()
        }
    }

    private fun showPassword(isShow: Boolean){
        if (isShow) {
            txpasswordconfRegist.transformationMethod = HideReturnsTransformationMethod.getInstance()
            IvShowHidePass1.setImageResource(R.drawable.ic_hide_password_24)
        }   else {
            txpasswordconfRegist.transformationMethod = PasswordTransformationMethod.getInstance()
            IvShowHidePass1.setImageResource(R.drawable.ic_show_password_24)
        }
        txpasswordconfRegist.setSelection(txpasswordconfRegist.text.toString().length)
    }
    fun backwelcomeActivity() {
        onBackPressed()
    }
}

