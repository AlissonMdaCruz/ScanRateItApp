package com.akhenaton.scanrateitapp.features.productratings.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhenaton.scanrateitapp.R
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import com.akhenaton.scanrateitapp.common.ui.BaseFragment
import com.akhenaton.scanrateitapp.common.ui.adapter.RatingAdapter
import com.akhenaton.scanrateitapp.databinding.FragmentProductRatingsBinding
import com.akhenaton.scanrateitapp.features.productratings.viewmodel.ProductRatingsViewModel
import com.akhenaton.scanrateitapp.features.productratings.viewmodel.ProductRatingsViewModelFactory
import com.akhenaton.scanrateitapp.features.productratings.viewmodel.ProductRatingsViewState

class ProductRatingsFragment : BaseFragment<FragmentProductRatingsBinding>() {

    private lateinit var viewModel: ProductRatingsViewModel
    private var ean: String = EMPTY

    override fun initView() {
        instanceViewModel()
        initObserver()
        initListener()
        ean = arguments?.getString(EAN) ?: EMPTY
        viewModel.getProductReviews(ean)
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductRatingsBinding = FragmentProductRatingsBinding.inflate(inflater, container, false)

    private fun instanceViewModel() {
        val factory = ProductRatingsViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[ProductRatingsViewModel::class.java]
    }

    private fun initObserver() {
        viewModel.state.observe(this) { state ->
            when(state) {
                ProductRatingsViewState.Loading -> onLoading()
                ProductRatingsViewState.Empty -> onEmptyList()
                ProductRatingsViewState.Error -> onError()
                is ProductRatingsViewState.Success -> onSuccess(state.list)
            }
        }
    }

    private fun initListener() {
        binding.btnProductRatingsTryAgain.setOnClickListener {
            viewModel.getProductReviews(ean)
        }
    }

    private fun onLoading() {
        binding.pgbSearchProductRatings.visibility = View.VISIBLE
        binding.txtProductRatingsEmpty.visibility = View.GONE
        binding.recyclerProductRatings.visibility = View.GONE
        binding.containerProductRatingsError.visibility = View.GONE
    }

    private fun onEmptyList() {
        binding.pgbSearchProductRatings.visibility = View.GONE
        binding.txtProductRatingsEmpty.visibility = View.VISIBLE
        binding.recyclerProductRatings.visibility = View.GONE
        binding.containerProductRatingsError.visibility = View.GONE
    }

    private fun onSuccess(list: List<ReviewModel>) {
        binding.pgbSearchProductRatings.visibility = View.GONE
        binding.txtProductRatingsEmpty.visibility = View.GONE
        binding.recyclerProductRatings.visibility = View.VISIBLE
        binding.containerProductRatingsError.visibility = View.GONE

        val recyclerView = binding.recyclerProductRatings
        val adapter = RatingAdapter(list, false, ::onItemSelect)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun onError() {
        binding.pgbSearchProductRatings.visibility = View.GONE
        binding.txtProductRatingsEmpty.visibility = View.GONE
        binding.recyclerProductRatings.visibility = View.GONE
        binding.containerProductRatingsError.visibility = View.VISIBLE
    }

    private fun onItemSelect(reviewModel: ReviewModel?) {
        val bundle = bundleOf(REVIEW to reviewModel)
        findNavController().navigate(R.id.action_product_ratings_to_review, bundle)
    }

    companion object {
        private const val EAN = "ean"
        private const val EMPTY = ""
        private const val REVIEW = "REVIEW"
    }
}
