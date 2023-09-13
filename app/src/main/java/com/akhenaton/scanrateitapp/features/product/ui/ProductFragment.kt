package com.akhenaton.scanrateitapp.features.product.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.akhenaton.scanrateitapp.common.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentProductBinding
import com.akhenaton.scanrateitapp.features.product.model.ProductModel

class ProductFragment : BaseFragment<FragmentProductBinding>() {
    override fun initView() {
        showDetails()
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductBinding = FragmentProductBinding.inflate(inflater, container, false)

    private fun showDetails() {
        val product = arguments?.getParcelable<ProductModel>(PRODUCT)
        binding.txtProductName.text = product?.nome
    }

    companion object {
        private const val PRODUCT = "product"
    }
}
