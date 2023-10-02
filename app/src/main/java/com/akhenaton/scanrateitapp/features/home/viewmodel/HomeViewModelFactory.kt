package com.akhenaton.scanrateitapp.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akhenaton.scanrateitapp.features.product.repository.ProductApi
import com.akhenaton.scanrateitapp.common.utils.NetworkUtils
import com.akhenaton.scanrateitapp.features.home.interactor.HomeInteractorImpl
import com.akhenaton.scanrateitapp.features.product.mapper.ProductMapperImpl

class HomeViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val api = NetworkUtils.getRetrofitInstance(EAN_BASE_URL).create(ProductApi::class.java)
        val mapper = ProductMapperImpl()
        val interactor = HomeInteractorImpl(api, mapper)

        return HomeViewModel(interactor) as T
    }

    companion object {
        private const val EAN_BASE_URL = "https://api-ean.hey.delivery/"
    }
}
