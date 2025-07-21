package com.nacho.spaceflight.challenge.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.spaceflight.challenge.R
import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.domain.usecase.GetArticleByIdUseCase
import com.nacho.spaceflight.challenge.domain.util.Result
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
            when (val result = getArticleByIdUseCase(id)) {
                is Result.Success -> {
                    val article = result.data
                    _uiState.value =
                        DetailUiState(isLoading = false, article = article, errorMessage = null)
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
}

data class DetailUiState(
    val isLoading: Boolean = true,
    val article: Article? = null,
    val errorMessage: Int? = null
)
