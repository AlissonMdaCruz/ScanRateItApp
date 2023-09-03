package com.akhenaton.scanrateitapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.akhenaton.scanrateitapp.common.BaseActivity
import com.akhenaton.scanrateitapp.databinding.ActivityMainBinding
import com.akhenaton.scanrateitapp.features.login.ui.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy {
        findNavController(R.id.nav_host_fragment_content_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checksUserLoggedIn()
        setupAppBarNavController()
        setListener()
    }

    private fun checksUserLoggedIn() {
        val auth = FirebaseAuth.getInstance()
        when (val currentUser = auth.currentUser) {
            null -> goToLogin()
            else -> showUserInfo(currentUser)
        }
    }

    private fun showUserInfo(currentUser: FirebaseUser) {
        val headerView = binding.navView.getHeaderView(FIRST_INDEX)
        val txtName = headerView.findViewById<TextView>(R.id.txt_header_name)
        val txtEmail = headerView.findViewById<TextView>(R.id.txt_header_email)
        txtName.text = currentUser.displayName
        txtEmail.text = currentUser.email
    }

    private fun setupAppBarNavController() {
        setSupportActionBar(binding.appBarMain.toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_ratings, R.id.nav_my_data
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun setListener() {
        binding.navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            logOut()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        goToLogin()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val FIRST_INDEX = 0
    }
}
