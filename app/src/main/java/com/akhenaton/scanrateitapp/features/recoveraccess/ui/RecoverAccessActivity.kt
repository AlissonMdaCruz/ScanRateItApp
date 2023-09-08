package com.akhenaton.scanrateitapp.features.recoveraccess.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.akhenaton.scanrateitapp.common.BaseActivity
import com.akhenaton.scanrateitapp.databinding.ActivityRecoverAccessBinding
import com.akhenaton.scanrateitapp.features.login.ui.LoginActivity
import com.akhenaton.scanrateitapp.features.recoveraccess.viewmodel.RecoverAccessViewModel
import com.akhenaton.scanrateitapp.features.recoveraccess.viewmodel.RecoverAccessViewModelFactory
import com.akhenaton.scanrateitapp.features.recoveraccess.viewmodel.RecoverAccessViewState

class RecoverAccessActivity : BaseActivity<ActivityRecoverAccessBinding>(
    ActivityRecoverAccessBinding::inflate
) {

    private lateinit var viewModel: RecoverAccessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initObserver()
        initListeners()
    }

    private fun initViewModel() {
        val factory = RecoverAccessViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[RecoverAccessViewModel::class.java]
    }

    private fun initObserver() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is RecoverAccessViewState.Loading -> onLoading()
                is RecoverAccessViewState.Success -> onSuccess()
                is RecoverAccessViewState.Error -> onError(state.message)
            }
        }
    }

    private fun onLoading() {
        TODO("Not yet implemented")
    }

    private fun onSuccess() {
        Toast.makeText(this, "email  de recuperação enviado", Toast.LENGTH_SHORT).show()
        goToLogin()
    }

    private fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initListeners() {
        binding.btnCancelRecover.setOnClickListener {
            goToLogin()
        }
        binding.btnRecoverAccess.setOnClickListener {
            val email = binding.edtEmailRecover.text.toString().trim()
            viewModel.recoverAccess(email)
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
