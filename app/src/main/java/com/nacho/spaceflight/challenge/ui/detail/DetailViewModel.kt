package com.nacho.spaceflight.challenge.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.usecase.GetArticleByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getArticleByIdUseCase: GetArticleByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    fun loadArticle(id: Int) {
        viewModelScope.launch {
            try {
                val article = getArticleByIdUseCase(id)
                _uiState.value = DetailUiState(isLoading = false, article = article)
            } catch (e: Exception) {
                _uiState.value = DetailUiState(isLoading = false, error = true)
            }
        }
    }
}

data class DetailUiState(
    val isLoading: Boolean = true,
    val article: Article? = null,
    val error: Boolean = false
)
