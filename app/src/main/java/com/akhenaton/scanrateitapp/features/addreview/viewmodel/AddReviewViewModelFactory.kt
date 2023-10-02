package com.akhenaton.scanrateitapp.features.addreview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akhenaton.scanrateitapp.common.repository.FirestoreRepositoryImpl

class AddReviewViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = FirestoreRepositoryImpl()
        return AddReviewViewModel(repository) as T
    }
}
