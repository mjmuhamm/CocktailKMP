package com.example.cocktailkmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
        val screenState by viewModel.state.collectAsStateWithLifecycle()
        var text by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .background(Color.White)
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


                    Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text("Enter drink...") },
                            modifier = Modifier.width(200.dp)

                        )

                        Button(onClick = { viewModel.fetchCocktails(text) }, shape = RoundedCornerShape(6.dp), modifier = Modifier.width(130.dp).padding(top = 7.dp, start = 16.dp)) {
                            Text("Search Cocktails")
                        }

                    }
                    when (val state = screenState) {
                        is ScreenState.Error -> {
                            Text("Error")
                        }

                        ScreenState.Loading -> {
                            CircularProgressIndicator()
                        }

                        ScreenState.Nothing -> {

                        }

                        is ScreenState.Success -> {
                            LazyColumn(modifier = Modifier.background(Color.White)) {
                                items((state as ScreenState.Success).drinks) { drink ->

//
                                            Column(Modifier.padding(start = 10.dp, top = 8.dp)) {
                                                Text("Drink: ${drink.strDrink}", fontWeight = FontWeight.Bold)
                                                Spacer(Modifier.height(5.dp))
                                                Text("Instructions: ${drink.strInstructions}")
                                                Spacer(Modifier.height(10.dp))
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

