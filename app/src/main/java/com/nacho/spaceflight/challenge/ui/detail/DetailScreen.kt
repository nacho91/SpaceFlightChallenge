package com.nacho.spaceflight.challenge.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nacho.spaceflight.challenge.domain.model.Article
import com.nacho.spaceflight.challenge.ui.components.Error

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    articleId: Int,
    onBack: () -> Unit
) {
    LaunchedEffect(articleId) {
        viewModel.loadArticle(articleId)
    }

    val state by viewModel.uiState.collectAsState()

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
                onRetryClick = { viewModel.loadArticle(articleId) }
            )
        }

        state.article != null -> {
            val article = state.article!!
            Column(modifier = modifier) {
                DetailTopAppBar(title = article.title, onBack = onBack)
                ArticleDetail(article = article)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit
) {
    LargeTopAppBar(
        modifier = modifier,
        title = {
            Text(text = title, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
            IconButton(
                onClick = { onBack() }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun ArticleDetail(
    modifier: Modifier = Modifier,
    article: Article
) {
    Column(modifier = modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = article.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(article.summary, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(article.authors.joinToString { it.name }, style = MaterialTheme.typography.bodyMedium)
    }
}