package com.akhenaton.scanrateitapp.features.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhenaton.scanrateitapp.features.home.interactor.FetchProductState
import com.akhenaton.scanrateitapp.features.home.interactor.HomeInteractor
import com.akhenaton.scanrateitapp.features.product.model.ProductModel
import kotlinx.coroutines.launch

class HomeViewModel(private val interactor: HomeInteractor) : ViewModel() {

    private val _mutableState = MutableLiveData<HomeViewState>()
    val state get() = _mutableState

    fun searchProduct(barCode: String) = viewModelScope.launch {
        _mutableState.postValue(HomeViewState.Loading)
        val result = interactor.fetchProduct(barCode)
        handleFetchProduct(result)
    }

    private fun handleFetchProduct(result: FetchProductState) {
        when(result) {
            is FetchProductState.DisplayEmpty -> displayEmpty()
            is FetchProductState.DisplayError -> displayError(result.message)
            is FetchProductState.DisplayProduct -> displaySuccess(result.product)
        }
    }

    private fun displayEmpty() {
        _mutableState.postValue(HomeViewState.Error(NOT_FOUND))
    }

    private fun displayError(message: String) {
        _mutableState.postValue(HomeViewState.Error(message))
    }

    private fun displaySuccess(product: ProductModel) {
        _mutableState.postValue(HomeViewState.Success(product))
    }

    fun clearState() {
        _mutableState.postValue(HomeViewState.Neutral)
    }

    companion object {
        private const val NOT_FOUND = "Produto não encontrado"
    }
}
