package com.kotlin.wonderwords.features.quotes.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotlin.wonderwords.features.quotes.domain.domain.Quote
import com.kotlin.wonderwords.features.quotes.domain.domain.QuoteCategory
import com.kotlin.wonderwords.features.quotes.domain.usecase.FetchQuotesUseCase
import com.kotlin.wonderwords.features.quotes.presentation.screen.QuotesUiEvents
import com.kotlin.wonderwords.features.quotes.presentation.screen.QuotesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val fetchQuotesUseCase: FetchQuotesUseCase
) : ViewModel() {

    private val _fetchQuotes = MutableStateFlow<PagingData<Quote>>(PagingData.empty())
    val fetchQuotes = _fetchQuotes.asStateFlow()

    private val _uiState = MutableStateFlow(QuotesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchQuotes(_uiState.value.selectedCategory)
    }

    fun onEvent(event: QuotesUiEvents) {
        when(event) {
            is QuotesUiEvents.SelectedCategory -> {
                if ( event.category != _uiState.value.selectedCategory ) {
                    _uiState.update {
                        it.copy(
                            selectedCategory = event.category
                        )
                    }
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
        }
    }
    private fun fetchQuotes(category: QuoteCategory) = viewModelScope.launch {
        fetchQuotesUseCase(category)
            .cachedIn(viewModelScope)
            .collect {
            _fetchQuotes.value = it
        }
    }
}