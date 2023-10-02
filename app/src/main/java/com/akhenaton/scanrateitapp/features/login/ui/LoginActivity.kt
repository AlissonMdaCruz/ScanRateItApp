package com.akhenaton.scanrateitapp.features.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.akhenaton.scanrateitapp.MainActivity
import com.akhenaton.scanrateitapp.features.register.ui.RegisterActivity
import com.akhenaton.scanrateitapp.common.ui.BaseActivity
import com.akhenaton.scanrateitapp.databinding.ActivityLoginBinding
import com.akhenaton.scanrateitapp.features.login.viewmodel.LoginViewModel
import com.akhenaton.scanrateitapp.features.login.viewmodel.LoginViewModelFactory
import com.akhenaton.scanrateitapp.features.login.viewmodel.LoginViewState
import com.akhenaton.scanrateitapp.features.recoveraccess.ui.RecoverAccessActivity

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
        binding.txtSignUp.setOnClickListener {
            goToRegister()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtLoginEmail.text.toString().trim()
            val password = binding.edtLoginPassword.text.toString().trim()
            viewModel.validateAccess(email, password)
        }

        binding.txtForgotPassword.setOnClickListener {
            goToRecoverAccess()
        }
    }

    private fun onLoading() {
        binding.pgbLogin.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        binding.pgbLogin.visibility = View.INVISIBLE
        goToMain()
    }

    private fun onError(message: String) {
        binding.pgbLogin.visibility = View.INVISIBLE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun goToRegister() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToRecoverAccess() {
        val intent = Intent(this@LoginActivity, RecoverAccessActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
