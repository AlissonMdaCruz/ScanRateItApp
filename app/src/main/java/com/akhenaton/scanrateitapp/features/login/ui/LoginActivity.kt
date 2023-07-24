package com.akhenaton.scanrateitapp.features.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.akhenaton.scanrateitapp.MainActivity
import com.akhenaton.scanrateitapp.features.register.ui.RegisterActivity
import com.akhenaton.scanrateitapp.common.BaseActivity
import com.akhenaton.scanrateitapp.databinding.ActivityLoginBinding
import com.akhenaton.scanrateitapp.features.login.viewmodel.LoginViewModel
import com.akhenaton.scanrateitapp.features.login.viewmodel.LoginViewModelFactory
import com.akhenaton.scanrateitapp.features.login.viewmodel.LoginViewState

class LoginActivity : BaseActivity<ActivityLoginBinding>(
    ActivityLoginBinding::inflate
) {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initObservers()
        initListeners()
    }

    public override fun onStart() {
        super.onStart()
        if (viewModel.currentUser != null) {
            goToMain()
        }
    }

    private fun initViewModel() {
        val factory = LoginViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is LoginViewState.Loading -> onLoading()
                is LoginViewState.Success -> onSuccess()
                is LoginViewState.Error -> onError(state.message)
            }
        }
    }

    private fun initListeners() {
        binding.txtRegisterNow.setOnClickListener {
            goToRegister()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtLoginEmail.text.toString()
            val password = binding.edtLoginPassword.text.toString()
            viewModel.validateAccess(email, password)
        }
    }

    private fun onLoading() {
        binding.pgbLogin.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        binding.pgbLogin.visibility = View.GONE
        Toast.makeText(this, LOGIN_SUCCESS, Toast.LENGTH_SHORT).show()
        goToMain()
    }

    private fun onError(message: String) {
        binding.pgbLogin.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun goToRegister() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val LOGIN_SUCCESS = "Login Successful"
    }
}
