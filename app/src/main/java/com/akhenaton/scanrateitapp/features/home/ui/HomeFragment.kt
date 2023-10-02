package com.akhenaton.scanrateitapp.features.home.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akhenaton.scanrateitapp.R
import com.akhenaton.scanrateitapp.common.ui.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentHomeBinding
import com.akhenaton.scanrateitapp.features.home.FragmentIntentIntegrator
import com.akhenaton.scanrateitapp.features.product.model.ProductModel
import com.akhenaton.scanrateitapp.features.home.viewmodel.HomeViewModel
import com.akhenaton.scanrateitapp.features.home.viewmodel.HomeViewModelFactory
import com.akhenaton.scanrateitapp.features.home.viewmodel.HomeViewState
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

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
        when (state) {
            HomeViewState.Loading -> showLoading()
            is HomeViewState.Error -> showError(state.message)
            is HomeViewState.Success -> showProductInfo(state.product)
            HomeViewState.Neutral -> cleanView()
        }
    }

    private fun cleanView() {
        binding.edtSearchProduct.text?.clear()
        binding.pgbSearchProduct.visibility = View.INVISIBLE
    }

    private fun showLoading() {
        binding.pgbSearchProduct.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        viewModel.clearState()
    }

    private fun showProductInfo(product: ProductModel) {
        try {
            val bundle = bundleOf(PRODUCT to product)
            findNavController().navigate(R.id.action_home_to_product, bundle)
            viewModel.clearState()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
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
        val scanner = FragmentIntentIntegrator(this)
        scanner.setDesiredBarcodeFormats(IntentIntegrator.EAN_13)
        scanner.setBeepEnabled(false) //retira o beep ao scannear
        scanner.initiateScan()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Leitura Cancelada", Toast.LENGTH_LONG).show()
            } else {
                viewModel.searchProduct(result.contents)
            }
        }
    }

    companion object {
        private const val PRODUCT = "product"
    }
}
