package com.nacho.spaceflight.challenge.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.spaceflight.challenge.R
import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.usecase.GetArticlesUseCase
import com.nacho.spaceflight.challenge.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        observeSearchQuery()
        fetchArticles("")
    }

    private fun fetchArticles(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = getArticlesUseCase(query)) {
                is Result.Success -> {
                    val articles = result.data
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        articles = articles,
                        errorMessage = null
                    )
                }

                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = when (result) {
                            is Result.Error.Network -> R.string.error_network
                            else -> R.string.error_unknown
                        }
                    )
                }
            }
        }
    }

    fun retry() {
        fetchArticles(searchQuery.value)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .onEach { query ->
                fetchArticles(query)
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}

data class ListUiState(
    val isLoading: Boolean = true,
    val articles: List<Article> = emptyList(),
    val errorMessage: Int? = null
)