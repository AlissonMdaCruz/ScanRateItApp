package com.akhenaton.scanrateitapp.features.changepass.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akhenaton.scanrateitapp.common.ui.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentChangePassBinding
import com.akhenaton.scanrateitapp.features.changepass.viewmodel.ChangePassViewModel
import com.akhenaton.scanrateitapp.features.changepass.viewmodel.ChangePassViewModelFactory
import com.akhenaton.scanrateitapp.features.changepass.viewmodel.ChangePassViewState

class ChangePassFragment : BaseFragment<FragmentChangePassBinding>() {

    private lateinit var viewModel: ChangePassViewModel

    override fun initView() {
        initViewModel()
        initObserver()
        initListeners()
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChangePassBinding = FragmentChangePassBinding.inflate(inflater, container, false)

    private fun initViewModel() {
        val factory = ChangePassViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[ChangePassViewModel::class.java]
    }

    private fun initObserver() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is ChangePassViewState.Loading -> onLoading()
                is ChangePassViewState.Success -> onSuccess()
                is ChangePassViewState.Error -> onError(state.message)
            }
        }
    }

    private fun initListeners() {
        binding.btnChangePass.setOnClickListener {
            val oldPass = binding.edtChangePasswordOld.text.toString().trim()
            val newPass = binding.edtChangePasswordNew.text.toString().trim()
            val confirmPass = binding.edtChangePasswordConfirm.text.toString().trim()
            viewModel.validateChangePass(oldPass, newPass, confirmPass)
        }

        binding.btnCancelChangePass.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onLoading() {
        binding.pgbChangePass.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        binding.pgbChangePass.visibility = View.INVISIBLE
        Toast.makeText(requireContext(), SUCCESS, Toast.LENGTH_SHORT).show()
        clearFields()
    }

    private fun clearFields() {
        binding.edtChangePasswordOld.text?.clear()
        binding.edtChangePasswordNew.text?.clear()
        binding.edtChangePasswordConfirm.text?.clear()
    }

    private fun onError(message: String) {
        binding.pgbChangePass.visibility = View.INVISIBLE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val SUCCESS = "Senha alterada com sucesso!"
    }
}
