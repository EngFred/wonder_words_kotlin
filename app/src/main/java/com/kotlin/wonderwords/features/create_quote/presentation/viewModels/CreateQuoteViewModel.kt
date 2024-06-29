package com.kotlin.wonderwords.features.create_quote.presentation.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.create_quote.domain.models.AddQuoteRequest
import com.kotlin.wonderwords.features.create_quote.domain.models.QuoteRequest
import com.kotlin.wonderwords.features.create_quote.domain.usecases.AddQuoteUseCase
import com.kotlin.wonderwords.features.create_quote.presentation.screens.CreateQuoteUiEvents
import com.kotlin.wonderwords.features.create_quote.presentation.screens.CreateQuoteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateQuoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addQuoteUseCase: AddQuoteUseCase
) : ViewModel() {


    private val username = savedStateHandle.get<String>("username")

    private val _uiState = MutableStateFlow(CreateQuoteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                author = username ?: ""
            )
        }
    }

    fun onEvent( event: CreateQuoteUiEvents ) {
        if ( _uiState.value.createError != null ) {
            _uiState.update {
                it.copy(
                    createError = null
                )
            }
        }
        when(event) {
            is CreateQuoteUiEvents.AuthorChanged -> {
                _uiState.update {
                    it.copy(
                        author = event.author
                    )
                }
                validateAuthor(_uiState.value.author)
            }
            is CreateQuoteUiEvents.BodyChanged -> {
                _uiState.update {
                    it.copy(
                        body = event.body
                    )
                }
                validateBody(_uiState.value.body)
            }
            CreateQuoteUiEvents.CreateButtonClicked -> {
                if ( _uiState.value.isValid ) {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    addQuote(_uiState.value.body, _uiState.value.author)
                }
            }
        }

        _uiState.update {
            it.copy(
                isValid = _uiState.value.bodyError.isNullOrEmpty() && uiState.value.authorError.isNullOrEmpty()
            )
        }
    }

    private fun addQuote(body: String, author: String) = viewModelScope.launch(Dispatchers.IO) {

        val quote = QuoteRequest(
            author = author.trim(),
            body = body.trim()
        )

        val quoteRequestBody = AddQuoteRequest(
            quote = quote
        )

        val  dataState = addQuoteUseCase(quoteRequestBody)

        when(dataState){
            is DataState.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        createError = dataState.error
                    )
                }
            }
            DataState.Loading -> Unit
            is DataState.Success -> {
                _uiState.update {
                    it.copy(
                        createdQuoteId = dataState.data
                    )
                }
            }
        }
    }


    private fun validateAuthor( name: String ) {
        if ( name.isEmpty() ) {
            _uiState.update {
                it.copy(
                    authorError = "Author can't be empty"
                )
            }
        } else {
            if ( name.length < 3) {
                _uiState.update {
                    it.copy(
                        authorError = "Author name is too short"
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        authorError = ""
                    )
                }
            }
        }
    }

    private fun validateBody( body: String ) {
        if ( body.isEmpty()  ) {
            _uiState.update {
                it.copy(
                    bodyError = "Body can't be empty"
                )
            }
        } else  {
            if ( body.length < 16) {
                _uiState.update {
                    it.copy(
                        bodyError = "The quote body is too short"
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        bodyError = ""
                    )
                }
            }
        }
    }
}