package com.akhenaton.scanrateitapp.features.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akhenaton.scanrateitapp.R
import com.akhenaton.scanrateitapp.common.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentHomeBinding
import com.akhenaton.scanrateitapp.features.product.model.ProductModel
import com.akhenaton.scanrateitapp.features.home.viewmodel.HomeViewModel
import com.akhenaton.scanrateitapp.features.home.viewmodel.HomeViewModelFactory
import com.akhenaton.scanrateitapp.features.home.viewmodel.HomeViewState

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var viewModel: HomeViewModel

    override fun initView() {
        instanceViewModel()
        initObserver()
        initListeners()
    }

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)

    private fun instanceViewModel() {
        val factory = HomeViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    private fun initObserver() {
        viewModel.state.observe(this) {
            setState(it)
        }
    }

    private fun setState(state: HomeViewState) {
        when(state) {
            HomeViewState.Loading -> showLoading()
            is HomeViewState.Error -> showError(state.message)
            is HomeViewState.Success -> showProductInfo(state.product)
        }
    }

    private fun showLoading() {
        binding.pgbSearchProduct.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        binding.pgbSearchProduct.visibility = View.INVISIBLE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showProductInfo(product: ProductModel) {
        binding.pgbSearchProduct.visibility = View.INVISIBLE
        val bundle = bundleOf(PRODUCT to product)
        findNavController().navigate(R.id.action_home_to_product, bundle)
    }

    private fun initListeners() {
        binding.btnSearchProduct.setOnClickListener {
            val code = binding.edtSearchProduct.text.toString()
            viewModel.searchProduct(code)
        }
        binding.btnBarcodeScan.setOnClickListener {
            openBarCodeScan()
        }
    }

    private fun openBarCodeScan() {
        // todo
    }

    companion object {
        private const val PRODUCT = "product"
    }
}
