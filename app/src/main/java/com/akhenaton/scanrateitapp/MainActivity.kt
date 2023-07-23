package com.akhenaton.scanrateitapp

import android.content.Intent
import android.os.Bundle
import com.akhenaton.scanrateitapp.common.BaseActivity
import com.akhenaton.scanrateitapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            goToLogin()
        } else {
            binding.txtUserDetails.text = currentUser.email
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            goToLogin()
        }
    }

    private fun goToLogin() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
