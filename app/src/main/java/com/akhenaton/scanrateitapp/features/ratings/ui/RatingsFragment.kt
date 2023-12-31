package com.akhenaton.scanrateitapp.features.ratings.ui

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
import com.akhenaton.scanrateitapp.databinding.FragmentRatingsBinding
import com.akhenaton.scanrateitapp.features.product.ui.ProductFragment
import com.akhenaton.scanrateitapp.features.ratings.viewmodel.RatingsViewModel
import com.akhenaton.scanrateitapp.features.ratings.viewmodel.RatingsViewModelFactory
import com.akhenaton.scanrateitapp.features.ratings.viewmodel.RatingsViewState

class RatingsFragment : BaseFragment<FragmentRatingsBinding>() {

    private lateinit var viewModel: RatingsViewModel

    override fun initView() {
        instanceViewModel()
        initObserver()
        initListener()
        viewModel.getUserReviews()
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRatingsBinding = FragmentRatingsBinding.inflate(inflater, container, false)

    private fun instanceViewModel() {
        val factory = RatingsViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[RatingsViewModel::class.java]
    }

    private fun initObserver() {
        viewModel.state.observe(this) { state ->
            when(state) {
                RatingsViewState.Loading -> onLoading()
                RatingsViewState.Empty -> onEmptyList()
                RatingsViewState.Error -> onError()
                is RatingsViewState.Success -> onSuccess(state.list)
            }
        }
    }

    private fun initListener() {
        binding.btnMyRatingsTryAgain.setOnClickListener {
            viewModel.getUserReviews()
        }
    }

    private fun onLoading() {
        binding.pgbSearchMyRatings.visibility = View.VISIBLE
        binding.txtMyRatingsEmpty.visibility = View.GONE
        binding.recyclerMyRatings.visibility = View.GONE
        binding.containerMyRatingsError.visibility = View.GONE
    }

    private fun onEmptyList() {
        binding.pgbSearchMyRatings.visibility = View.GONE
        binding.txtMyRatingsEmpty.visibility = View.VISIBLE
        binding.recyclerMyRatings.visibility = View.GONE
        binding.containerMyRatingsError.visibility = View.GONE
    }

    private fun onSuccess(list: List<ReviewModel>) {
        binding.pgbSearchMyRatings.visibility = View.GONE
        binding.txtMyRatingsEmpty.visibility = View.GONE
        binding.recyclerMyRatings.visibility = View.VISIBLE
        binding.containerMyRatingsError.visibility = View.GONE

        val recyclerView = binding.recyclerMyRatings
        val adapter = RatingAdapter(list, true, ::onItemSelect)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun onError() {
        binding.pgbSearchMyRatings.visibility = View.GONE
        binding.txtMyRatingsEmpty.visibility = View.GONE
        binding.recyclerMyRatings.visibility = View.GONE
        binding.containerMyRatingsError.visibility = View.VISIBLE
    }

    private fun onItemSelect(reviewModel: ReviewModel?) {
        val bundle = bundleOf(REVIEW to reviewModel)
        findNavController().navigate(R.id.action_ratings_to_review, bundle)
    }

    companion object {
        private const val REVIEW = "REVIEW"
    }
}
