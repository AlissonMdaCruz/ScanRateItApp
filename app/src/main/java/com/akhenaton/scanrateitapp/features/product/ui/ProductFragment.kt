package com.akhenaton.scanrateitapp.features.product.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.akhenaton.scanrateitapp.R
import com.akhenaton.scanrateitapp.common.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentProductBinding
import com.akhenaton.scanrateitapp.features.product.model.ProductModel

class ProductFragment : BaseFragment<FragmentProductBinding>() {

    private var product: ProductModel? = null

    override fun initView() {
        showDetails()
        setlisteners()
    }

    private fun setlisteners() {
        binding.rbrProduct.setOnClickListener {
            Toast.makeText(requireContext(), "${(it as RatingBar).rating}", Toast.LENGTH_SHORT).show()
        }
        binding.btnGoToAddReview.setOnClickListener {
            val bundle = bundleOf(PRODUCT to product)
            findNavController().navigate(R.id.action_product_to_add_review, bundle)
        }
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductBinding = FragmentProductBinding.inflate(inflater, container, false)

    private fun showDetails() {
        product = arguments?.getParcelable(PRODUCT)
        binding.txtProductCode.text = product?.code
        binding.txtProductName.text = product?.nome
        binding.rbrProduct.rating = 4.5F
    }

    companion object {
        private const val PRODUCT = "product"
    }
}
