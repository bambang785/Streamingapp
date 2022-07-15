package com.example.streamingapp1

import android.content.ContentProviderClient
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.streamingapp1.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class Login : AppCompatActivity() {
    private var mIsShowPass = false
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    //constants
    private companion object{
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root;
        setContentView(view)
        initdata();

        //configure the google signin
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)


        val button = findViewById<ImageButton>(R.id.backbutton)
        button.setOnClickListener { v: View? -> backwelcomeActivity() }


        IvShowHidePass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }

    }

    private fun  initdata(){
        auth = FirebaseAuth.getInstance();
        checkUser()
        clickListener()
    }



    private fun  clickListener(){
        binding.signupbuttonlogin.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
            finish()
        }

        binding.buttonsigninlogin.setOnClickListener {
            getUserData()
        }
        binding.googlesignin.setOnClickListener {
            Log.d(TAG,"onCreate: begin Google SignIn")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
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

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null){
            startActivity(Intent(this@Login, Dashboard1::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode ==  RC_SIGN_IN){
            Log.d(TAG, "onActivityResult: Google SIgnIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount (account)
            }
            catch (e: Exception){
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG,"firebaseAuthWithGoogleAccount: begin firebase auth with google account")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->

                Log.d(TAG,"firebaseAuthWithGoogleAccount: LoggedIn")

                val firebaseUser = auth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                Log.d(TAG,"firebaseAuthWithGoogleAccount: Uid: $uid")
                Log.d(TAG,"firebaseAuthWithGoogleAccount: Email: $email")

                if (authResult.additionalUserInfo!!.isNewUser){
                    Log.d(TAG,"firebaseAuthWithGoogleAccount: Account created... \n#$email")
                    Toast.makeText(this@Login, "Account created... \n$email", Toast.LENGTH_SHORT).show()
                }
                else{
                   Log.d(TAG, "firebaseAuthWithGoogleAccount: Existing user... \n$email")
                    Toast.makeText(this@Login, "LoggedIn... \n$email", Toast.LENGTH_SHORT).show()
                }
                //start
                startActivity(Intent(this@Login, Dashboard1::class.java))
            }
            .addOnFailureListener{e ->
                Log.d(TAG, "firebaseAuthGoogleAccount: Loggin Failed due to ${e.message}")
                Toast.makeText(this@Login, "Loggin Failed due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

}