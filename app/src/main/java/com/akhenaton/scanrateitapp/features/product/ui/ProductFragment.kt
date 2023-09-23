package com.akhenaton.scanrateitapp.features.product.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akhenaton.scanrateitapp.R
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import com.akhenaton.scanrateitapp.common.ui.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentProductBinding
import com.akhenaton.scanrateitapp.features.product.model.ProductModel
import com.akhenaton.scanrateitapp.features.product.viewmodel.ProductViewModel
import com.akhenaton.scanrateitapp.features.product.viewmodel.ProductViewModelFactory
import com.akhenaton.scanrateitapp.features.product.viewmodel.ProductViewState

class ProductFragment : BaseFragment<FragmentProductBinding>() {

    private lateinit var viewModel: ProductViewModel
    private var product: ProductModel? = null
    private var review: ReviewModel? = null

    override fun initView() {
        instanceViewModel()
        initObserver()
        setlisteners()
        product = arguments?.getParcelable(PRODUCT)
        viewModel.getProductReviews(product?.code ?: "")
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductBinding = FragmentProductBinding.inflate(inflater, container, false)

    private fun instanceViewModel() {
        val factory = ProductViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
    }

    private fun initObserver() {
        viewModel.state.observe(this) { state ->
            when(state) {
                ProductViewState.Loading -> onLoading()
                ProductViewState.Error -> onError()
                is ProductViewState.Success -> onSuccess(state.list)
            }
        }
    }

    private fun onLoading() {
        binding.pgbProduct.visibility = View.VISIBLE
        binding.containerProductError.visibility = View.GONE
        binding.containerProductContent.visibility = View.GONE
    }

    private fun onError() {
        binding.pgbProduct.visibility = View.GONE
        binding.containerProductError.visibility = View.VISIBLE
        binding.containerProductContent.visibility = View.GONE
    }

    private fun onSuccess(list: List<ReviewModel>) {
        binding.pgbProduct.visibility = View.GONE
        binding.containerProductError.visibility = View.GONE
        binding.containerProductContent.visibility = View.VISIBLE
        showDetails(list)
    }

    private fun setlisteners() {
        binding.imgShareReview.setOnClickListener {
            // todo share
        }
        binding.btnGoToReviews.setOnClickListener {
            val bundle = bundleOf(EAN to product?.code)
            findNavController().navigate(R.id.action_product_to_product_ratings, bundle)
        }
        binding.btnGoToAddReview.setOnClickListener {
            val bundle = bundleOf(PRODUCT to product)
            findNavController().navigate(R.id.action_product_to_add_review, bundle)
        }
        binding.btnGoToMyReview.setOnClickListener {
            // todo goto my review
        }
    }

    private fun showDetails(list: List<ReviewModel>) {
        product = arguments?.getParcelable(PRODUCT)
        review = list.find { it.userId == viewModel.currentUser?.uid }
        binding.txtProductCode.text = product?.code
        binding.txtProductName.text = product?.nome
        showRatings(list)
        when (review) {
            null -> showAddReviewButton()
            else -> showMyReviewButton()
        }
    }

    private fun showRatings(list: List<ReviewModel>) {
        when {
            list.isEmpty() -> {
                binding.btnGoToReviews.isEnabled = false
            }
            else -> {
                binding.btnGoToReviews.isEnabled = true
                binding.rbrProduct.rating = list.map { it.rating }.average().toFloat()
            }
        }
    }

    private fun showAddReviewButton() {
        binding.btnGoToAddReview.visibility = View.VISIBLE
        binding.btnGoToMyReview.visibility = View.GONE
    }

    private fun showMyReviewButton() {
        binding.btnGoToAddReview.visibility = View.GONE
        binding.btnGoToMyReview.visibility = View.VISIBLE
    }

    companion object {
        private const val PRODUCT = "product"
        private const val EAN = "ean"
    }
}
