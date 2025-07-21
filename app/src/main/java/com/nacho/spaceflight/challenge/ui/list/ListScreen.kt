package com.nacho.spaceflight.challenge.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nacho.spaceflight.challenge.R.string.list_no_results
import com.nacho.spaceflight.challenge.R.string.list_search_placeholder
import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.ui.components.Error

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    onArticleClick: (Int) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val query by viewModel.searchQuery.collectAsState()

    when {
        state.isLoading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            Error(
                modifier = Modifier.fillMaxSize(),
                message = stringResource(state.errorMessage!!),
                onRetryClick = { viewModel.retry() }
            )
        }

        else -> {
            Column(modifier = modifier.fillMaxSize()) {
                SearchTopAppBar(
                    searchQuery = query,
                    onSearchQueryChange = {
                        viewModel.onSearchQueryChanged(it)
                    })

                val articles = state.articles

                if (articles.isNotEmpty()) {
                    ArticleList(
                        modifier = modifier.weight(1f),
                        articles = articles,
                        onArticleClick = onArticleClick
                    )
                } else {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(list_no_results),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = searchQuery,
                onValueChange = { onSearchQueryChange(it) },
                placeholder = {
                    Text(
                        text = stringResource(list_search_placeholder),
                    )
                },
                singleLine = true,
            )
        }
    )
}

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onArticleClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(articles) { article ->
            ArticleCard(
                modifier = Modifier.padding(16.dp),
                article = article,
                onArticleClick = onArticleClick
            )
        }
    }

}

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onArticleClick: (Int) -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        onClick = { onArticleClick(article.id) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(120.dp),
                model = article.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = article.title
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = article.authors.joinToString(separator = ",") { it.name }
                )
            }
        }
    }
}