package com.akhenaton.scanrateitapp.features.register.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.akhenaton.scanrateitapp.MainActivity
import com.akhenaton.scanrateitapp.common.ui.BaseActivity
import com.akhenaton.scanrateitapp.databinding.ActivityRegisterBinding
import com.akhenaton.scanrateitapp.features.login.ui.LoginActivity
import com.akhenaton.scanrateitapp.features.register.viewmodel.RegisterViewModel
import com.akhenaton.scanrateitapp.features.register.viewmodel.RegisterViewModelFactory
import com.akhenaton.scanrateitapp.features.register.viewmodel.RegisterViewState

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(
    ActivityRegisterBinding::inflate
) {

    private lateinit var viewModel: RegisterViewModel

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
        val factory = RegisterViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
    }

    private fun initObservers() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is RegisterViewState.Loading -> onLoading()
                is RegisterViewState.Success -> onSuccess()
                is RegisterViewState.Error -> onError(state.message)
            }
        }
    }

    private fun initListeners() {
        binding.btnCancelRegister.setOnClickListener {
            goToLogin()
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.edtRegisterName.text.toString().trim()
            val email = binding.edtRegisterEmail.text.toString().trim()
            val password = binding.edtRegisterPassword.text.toString().trim()
            val confirmPassword = binding.edtRegisterConfirmPassword.text.toString().trim()
            viewModel.validateSignUp(name, email, password, confirmPassword)
        }
    }

    private fun onLoading() {
        binding.pgbRegister.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        binding.pgbRegister.visibility = View.INVISIBLE
        Toast.makeText(this, CREATE_SUCCESS, Toast.LENGTH_SHORT).show()
        goToMain()
    }

    private fun onError(message: String) {
        binding.pgbRegister.visibility = View.INVISIBLE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val CREATE_SUCCESS = "Create Successful"
    }
}
