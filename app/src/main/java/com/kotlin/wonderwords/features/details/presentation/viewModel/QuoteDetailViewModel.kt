package com.kotlin.wonderwords.features.details.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.details.domain.usecase.DownvoteQuoteUseCase
import com.kotlin.wonderwords.features.details.domain.usecase.FavQuoteUseCase
import com.kotlin.wonderwords.features.details.domain.usecase.FetchQuoteDetailsUseCase
import com.kotlin.wonderwords.features.details.domain.usecase.UnFavQuoteUseCase
import com.kotlin.wonderwords.features.details.domain.usecase.UpvoteQuoteUseCase
import com.kotlin.wonderwords.features.details.presentation.screen.QuoteDetailEvents
import com.kotlin.wonderwords.features.details.presentation.screen.QuoteDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteDetailViewModel @Inject constructor(
    private val fetchQuoteDetailsUseCase: FetchQuoteDetailsUseCase,
    private val favQuoteDetailsUseCase: FavQuoteUseCase,
    private val unFavQuoteDetailsUseCase: UnFavQuoteUseCase,
    private val upvoteQuoteUseCase: UpvoteQuoteUseCase,
    private val downvoteQuoteUseCase: DownvoteQuoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val quoteId = savedStateHandle.get<Int>("quoteId")

    private val _uiState = MutableStateFlow(QuoteDetailsUiState())
    val uiState = _uiState.asStateFlow()
    init {
        if ( quoteId != null ) {
            fetchQuoteDetails(quoteId)
        }
    }

    fun onEvent( event : QuoteDetailEvents ) {
        when(event){
            QuoteDetailEvents.Downvoted -> {
                if (_uiState.value.quote?.userDetails?.downvoted == false) {
                    downvoteQuote(quoteId!!)
                }
            }
            QuoteDetailEvents.FavClicked -> {
                if (_uiState.value.quote?.userDetails?.favorited == true) {
                    unFavQuote(quoteId!!)
                } else {
                    favQuote(quoteId!!)
                }
            }
            QuoteDetailEvents.Upvoted -> {
                if (_uiState.value.quote?.userDetails?.upvoted == false) {
                    upvoteQuote(quoteId!!)
                }
            }

            QuoteDetailEvents.RetryClicked -> {
                quoteId?.let {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                    fetchQuoteDetails(quoteId)
                }
            }
        }
    }

    private fun fetchQuoteDetails(quoteId: Int) = viewModelScope.launch {
        fetchQuoteDetailsUseCase(quoteId).collectLatest { dataState ->
            when(dataState){
                is DataState.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = dataState.error
                        )
                    }
                }
                DataState.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is DataState.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            quote = dataState.data
                        )
                    }
                }
            }
        }
    }

    private fun favQuote(quoteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val updatedQuote = favQuoteDetailsUseCase(quoteId)

        if ( updatedQuote != null ) {
            _uiState.update {
                it.copy(
                    quote = updatedQuote,
                )
            }
        }
    }

    private fun unFavQuote(quoteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val updatedQuote = unFavQuoteDetailsUseCase(quoteId)

        if ( updatedQuote != null ) {
            _uiState.update {
                it.copy(
                    quote = updatedQuote,
                )
            }
        }
    }

    private fun upvoteQuote(quoteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val updatedQuote = upvoteQuoteUseCase(quoteId)
        if ( updatedQuote != null ) {
            _uiState.update {
                it.copy(
                    quote = updatedQuote,
                )
            }
        }
    }

    private fun downvoteQuote(quoteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val updatedQuote = downvoteQuoteUseCase(quoteId)
        if ( updatedQuote != null ) {
            _uiState.update {
                it.copy(
                    quote = updatedQuote,
                )
            }
        }
    }

}