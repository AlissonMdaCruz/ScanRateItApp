package com.akhenaton.scanrateitapp.features.review.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akhenaton.scanrateitapp.R
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import com.akhenaton.scanrateitapp.common.ui.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentReviewBinding
import com.akhenaton.scanrateitapp.features.review.viewmodel.ReviewViewModel
import com.akhenaton.scanrateitapp.features.review.viewmodel.ReviewViewModelFactory
import com.akhenaton.scanrateitapp.features.review.viewmodel.ReviewViewState

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {

    private var review: ReviewModel? = null
    private var isMyReview = false

    private lateinit var viewmodel: ReviewViewModel

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReviewBinding = FragmentReviewBinding.inflate(inflater, container, false)

    override fun initView() {
        instanceViewModel()
        initObserver()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        getArgs()
        displayDetails()
    }

    private fun instanceViewModel() {
        val factory = ReviewViewModelFactory()
        viewmodel = ViewModelProvider(this, factory)[ReviewViewModel::class.java]
    }

    private fun initObserver() {
        viewmodel.state.observe(this) { state ->
            when(state) {
                ReviewViewState.Loading -> onLoading()
                ReviewViewState.Success -> onSuccess()
                is ReviewViewState.Error -> onError(state.message)
            }
        }
    }

    private fun getArgs() {
        review = arguments?.getParcelable(REVIEW)
        isMyReview = review?.userId == viewmodel.currentUser?.uid
    }

    private fun displayDetails() {
        review?.let {
            binding.txtReviewProductName.text = it.product
            binding.txtReviewUserName.text = it.userName
            binding.txtReview.text = it.review
            binding.rbrReview.rating = it.rating
        }

        when {
            isMyReview -> binding.containerReviewButtons.visibility = View.VISIBLE
            else -> binding.containerReviewButtons.visibility = View.GONE
        }
    }

    private fun setListeners() {
        binding.btnDeleteReview.setOnClickListener {
            review?.let { reviewModel -> viewmodel.deleteReview(reviewModel) }
        }
        binding.btnEditReview.setOnClickListener {
            val bundle = bundleOf(REVIEW to review)
            findNavController().navigate(R.id.action_review_to_edit_review, bundle)
        }
    }

    private fun onLoading() {
        binding.pgbDeleteReview.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        binding.pgbDeleteReview.visibility = View.GONE
        Toast.makeText(requireContext(), SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun onError(message: String) {
        binding.pgbDeleteReview.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REVIEW = "REVIEW"
        private const val SUCCESS_MESSAGE = "Avaliação deletada com sucesso!"
    }
}
