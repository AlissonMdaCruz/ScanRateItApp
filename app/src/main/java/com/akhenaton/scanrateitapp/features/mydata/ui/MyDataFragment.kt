package com.akhenaton.scanrateitapp.features.mydata.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akhenaton.scanrateitapp.R
import com.akhenaton.scanrateitapp.common.ui.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentMyDataBinding
import com.akhenaton.scanrateitapp.features.mydata.viewmodel.MyDataViewModel
import com.akhenaton.scanrateitapp.features.mydata.viewmodel.MyDataViewState
import com.google.firebase.auth.FirebaseUser

class MyDataFragment : BaseFragment<FragmentMyDataBinding>() {

    private lateinit var viewModel: MyDataViewModel

    override fun initView() {
        instanceViewModel()
        initObserver()
        initListeners()
        viewModel.fetchData()
    }

    private fun instanceViewModel() {
        viewModel = ViewModelProvider(this)[MyDataViewModel::class.java]
    }

    private fun initObserver() {
        viewModel.state.observe(this) { state ->
            when(state) {
                is MyDataViewState.ShowUser -> showData(state.user)
                is MyDataViewState.Error -> showError(state.message)
            }
        }
    }

    private fun initListeners() {
        binding.btnMyDataChange.setOnClickListener {
            findNavController().navigate(R.id.action_data_to_change_data)
        }
    }

    private fun showData(user: FirebaseUser) {
        binding.txtMyDataNameValue.text = user.displayName
        binding.txtMyDataEmailValue.text = user.email
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyDataBinding = FragmentMyDataBinding.inflate(inflater, container, false)
}
