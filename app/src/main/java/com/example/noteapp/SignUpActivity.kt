package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.noteapp.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()
        binding.signupLoginBtn.setOnClickListener {
            finish()
        }

        binding.signupBtn.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPass.text.toString()
            val cfmPassword = binding.signupCfm.text.toString()

            if (password != cfmPassword)
                return@setOnClickListener
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                Snackbar.make(binding.root, "Sign Up Success!", Snackbar.LENGTH_LONG).show()
                                Intent(this, NoteActivity::class.java).also {
                                    startActivity(it)
                                    finishAffinity()
                                }
                            }
                    } else {
                        Snackbar.make(binding.root, task.exception.toString(), Snackbar.LENGTH_LONG).toString()
                    }
                }
        }
    }
}