package com.akhenaton.scanrateitapp.features.review.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akhenaton.scanrateitapp.common.repository.model.ReviewModel
import com.akhenaton.scanrateitapp.common.ui.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentReviewBinding

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {

    private var review: ReviewModel? = null
    private var isMyReview = false

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReviewBinding = FragmentReviewBinding.inflate(inflater, container, false)

    override fun initView() {
        getArgs()
        displayDetails()
        setListeners()
    }

    private fun getArgs() {
        review = arguments?.getParcelable(REVIEW)
        isMyReview = arguments?.getBoolean(MY_REVIEW) == true
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
            // todo
        }
        binding.btnEditReview.setOnClickListener {
            // todo
        }
    }

    companion object {
        private const val REVIEW = "REVIEW"
        private const val MY_REVIEW = "myreview"
    }
}
