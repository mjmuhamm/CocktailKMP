package com.example.cocktailkmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource

import cocktailkmp.composeapp.generated.resources.Res
import cocktailkmp.composeapp.generated.resources.compose_multiplatform
import coil3.compose.AsyncImage

import com.example.cocktailapi.states.ScreenState
import com.example.cocktailapi.viewModel.CocktailViewModel
import com.example.cocktailapi.viewModel.cocktailModelFactory

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(true) }

        val viewModel = viewModel<CocktailViewModel>(factory = cocktailModelFactory)
        val state by viewModel.state.collectAsStateWithLifecycle()
        var text by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {


                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text("Enter drink...") }

                        )

                        Button(onClick = { viewModel.fetchCocktails(text) }) {
                            Text("Search Cocktails")
                        }

                    }
                    when (state) {
                        is ScreenState.Error -> {
                            Text("Error")
                        }

                        ScreenState.Loading -> {
                            Text("Loading")
                        }

                        ScreenState.Nothing -> {
                            Text("Nothing is Happening")
                        }

                        is ScreenState.Success -> {
                            Text("Happening")

                            LazyColumn() {
                                items((state as ScreenState.Success).drinks) { drink ->
                                    Card() {
                                        Row() {
//                                            AsyncImage(
//                                                model = drink.strDrinkThumb,
//                                                contentDescription = "",
//                                                modifier = Modifier.size(75.dp),
//                                            )
                                            Column() {
                                                Text("Margarita${drink.strDrink}")
                                                Text("Instructions${drink.strInstructions}")
                                            }

                                        }
//                                    }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}