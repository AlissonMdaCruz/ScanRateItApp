package com.akhenaton.scanrateitapp.features.addreview.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.akhenaton.scanrateitapp.common.ui.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentAddReviewBinding
import com.akhenaton.scanrateitapp.features.addreview.viewmodel.AddReviewViewModel
import com.akhenaton.scanrateitapp.features.addreview.viewmodel.AddReviewViewModelFactory
import com.akhenaton.scanrateitapp.features.addreview.viewmodel.AddReviewViewState
import com.akhenaton.scanrateitapp.features.product.model.ProductModel

class AddReviewFragment : BaseFragment<FragmentAddReviewBinding>() {

    private lateinit var viewModel: AddReviewViewModel
    private var product: ProductModel? = null

    override fun initView() {
        showDetails()
        instanceViewModel()
        initObserver()
        setListeners()
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddReviewBinding = FragmentAddReviewBinding.inflate(inflater, container, false)

    private fun showDetails() {
        product = arguments?.getParcelable(PRODUCT)
        binding.txtAddReviewName.text = product?.nome
    }

    private fun instanceViewModel() {
        val factory = AddReviewViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[AddReviewViewModel::class.java]
    }

    private fun initObserver() {
        viewModel.state.observe(this) { state ->
            when (state) {
                AddReviewViewState.Loading -> onLoading()
                AddReviewViewState.Success -> onSuccess()
                is AddReviewViewState.Error -> onError(state.message)
            }
        }
    }

    private fun onLoading() {
        binding.pgbAddReview.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        binding.pgbAddReview.visibility = View.INVISIBLE
        Toast.makeText(requireContext(), "Review enviada com sucesso!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun onError(message: String) {
        binding.pgbAddReview.visibility = View.INVISIBLE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setListeners() {
        binding.btnAddReview.setOnClickListener {
            product?.let {
                viewModel.validateReview(
                    it.code,
                    it.nome,
                    binding.edtAddReview.text.toString(),
                    binding.rbrAddReview.rating
                )
            }
        }
        binding.btnCancelAddReview.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        private const val PRODUCT = "product"
    }
}
