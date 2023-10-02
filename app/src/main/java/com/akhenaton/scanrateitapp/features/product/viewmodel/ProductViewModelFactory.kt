package com.akhenaton.scanrateitapp.features.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akhenaton.scanrateitapp.common.repository.FirestoreRepositoryImpl

class ProductViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = FirestoreRepositoryImpl()
        return ProductViewModel(repository) as T
    }
}
