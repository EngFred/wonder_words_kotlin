package com.kotlin.wonderwords.features.quotes.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kotlin.wonderwords.features.quotes.domain.entity.Quote
import com.kotlin.wonderwords.features.quotes.domain.usecase.FetchQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val fetchQuotesUseCase: FetchQuotesUseCase
) : ViewModel() {

    private val _fetchQuotes = MutableStateFlow<PagingData<Quote>>(PagingData.empty())
    val fetchQuotes = _fetchQuotes.asStateFlow()

    init {
        fetchQuotes()
    }

    private fun fetchQuotes() = viewModelScope.launch {
        fetchQuotesUseCase()
            .cachedIn(viewModelScope)
            .collect {
            _fetchQuotes.value = it
        }
    }
}