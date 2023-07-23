package com.akhenaton.scanrateitapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.akhenaton.scanrateitapp.common.BaseActivity
import com.akhenaton.scanrateitapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(
    ActivityRegisterBinding::inflate
) {

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToMain()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.txtLoginNow.setOnClickListener {
            goToLogin()
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edtRegisterEmail.text.toString()
            val password = binding.edtRegisterPassword.text.toString()
            validateInputs(email, password)
        }
    }

    private fun validateInputs(email: String, password: String) {
        if (email.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Empty email", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Empty password", Toast.LENGTH_SHORT).show()
        } else {
            validateEmail(email, password)
        }
    }

    private fun validateEmail(email: String, password: String) {
        binding.pgbRegister.visibility = View.VISIBLE
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.pgbRegister.visibility = View.GONE
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        this@RegisterActivity,
                        "Create succesfully.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    goToLogin()
                    //val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this@RegisterActivity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun goToLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMain() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
