package com.aaron.pigeons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aaron.pigeons.constants.Constants
import com.aaron.pigeons.remote.dto.PigeonDto
import com.aaron.pigeons.ui.theme.PigeonsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PigeonsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: PigeonViewModel by viewModels()
                    PigeonsPage(viewModel)
                }
            }
        }
    }
}

@Composable
fun PigeonsPage(viewModel: PigeonViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            for (category in uiState.categories) {
                Button(
                    onClick = {
                        viewModel.selectCategory(category)
                    },
                    modifier = Modifier
                        .aspectRatio(1.0f)
                        .fillMaxSize()
                        .weight(1.0f),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        focusedElevation = 0.dp
                    )
                )
                {
                    Text(category)
                }
            }
        }
        AnimatedVisibility(uiState.selectedPigeons.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp),
                content = {
                    items(uiState.selectedPigeons) {
                        BirdImageCell(pigeon = it)
                    }
                }
            )
        }
    }

}

@Composable
fun BirdImageCell(pigeon: PigeonDto) {
    AsyncImage(
        model = "${Constants.BASE_URL}/${pigeon.path}",
        "${pigeon.category} by ${pigeon.author}",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.0f)
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PigeonsTheme {
        Greeting("Android")
    }
}