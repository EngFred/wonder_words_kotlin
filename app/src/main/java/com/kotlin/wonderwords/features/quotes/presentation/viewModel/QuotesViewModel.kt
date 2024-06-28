package com.kotlin.wonderwords.features.quotes.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.quotes.domain.models.QuoteCategory
import com.kotlin.wonderwords.features.quotes.domain.models.Source
import com.kotlin.wonderwords.features.quotes.domain.usecase.FetchQuotesUseCase
import com.kotlin.wonderwords.features.quotes.presentation.screen.QuotesUiEvents
import com.kotlin.wonderwords.features.quotes.presentation.screen.QuotesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val fetchQuotesUseCase: FetchQuotesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuotesUiState())
    val uiState = _uiState.asStateFlow()

    private var currentPage = 1
    private var isLoading = false

    init {
        fetchQuotes(_uiState.value.selectedCategory)
    }

    fun onEvent(event: QuotesUiEvents) {
        when(event) {
            is QuotesUiEvents.SelectedCategory -> {
                if ( event.category != _uiState.value.selectedCategory ) {
                    _uiState.update {
                        it.copy(
                            selectedCategory = event.category,
                            initialLoading = true,
                            quotes = emptyList()
                        )
                    }
                    currentPage = 1
                    fetchQuotes(event.category)
                }
            }
            is QuotesUiEvents.SearchQueryChanged -> {
                _uiState.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
            }

            QuotesUiEvents.LoadedMoreQuotes -> {
                fetchQuotes(_uiState.value.selectedCategory)
            }
        }
    }
    private fun fetchQuotes(category: QuoteCategory){
        if (isLoading) return
        viewModelScope.launch(Dispatchers.IO) {
            val dataState = fetchQuotesUseCase(currentPage, category)

            when(dataState) {
                is DataState.Error -> {
                    _uiState.update {
                        it.copy(
                            refreshError = dataState.error,
                            initialLoading = false
                        )
                    }
                }
                DataState.Loading -> Unit
                is DataState.Success -> {
                    Log.v("okhttp.OkHttpClient", "Data source is ${dataState.data.source}")
                    val newQuotes = when{
                        dataState.data.source == Source.Remote -> _uiState.value.quotes + dataState.data.data
                        (dataState.data.source == Source.Cache) && (currentPage == 1 ) -> dataState.data.data
                        else -> _uiState.value.quotes
                    }
                    _uiState.update {
                        it.copy(
                            quotes = newQuotes,
                            initialLoading = false,
                            source = dataState.data.source
                        )
                    }
                    currentPage++
                    isLoading = false

                }
            }

        }
    }
}