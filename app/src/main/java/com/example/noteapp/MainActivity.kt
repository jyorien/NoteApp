package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.noteapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            Intent(this, NoteActivity::class.java).also {
                startActivity(it)
                finishAffinity()
            }
        }
        binding.loginSignupBtn.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)

            }
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val pass = binding.loginEmail.text.toString().trim()
//            if (email.isEmpty() || pass.isEmpty())
//                return@setOnClickListener
            mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Intent(this, NoteActivity::class.java)
                            .also {
                                startActivity(it)
                                finishAffinity()
                            }

                    } else {
                        Snackbar.make(binding.root, task.exception.toString(), Snackbar.LENGTH_LONG).show()
                    }
                }

        }
    }
}