package com.example.cocktailapi.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktailapi.model.CocktailResponse
import com.example.cocktailapi.model.Drink
import com.example.cocktailapi.remote.Network
import com.example.cocktailapi.states.DrinksResult
import com.example.cocktailapi.states.ScreenState
import com.example.cocktailkmp.repository.CocktailRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailViewModel(private val repository : CocktailRepositoryImpl = CocktailRepositoryImpl(
    Network.networkClient
)) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Nothing)
    val state = _state.asStateFlow()


    fun fetchCocktails(name: String) {
        viewModelScope.launch(Dispatchers.IO) {

            _state.update {
                ScreenState.Loading
            }
            val response = repository.getCocktailsByName(name)
            println("This is the ${response}")
            when (val response = repository.getCocktailsByName(name)) {
                is DrinksResult.Success<*> -> {
                    _state.update {
                        ScreenState.Success(response.data as List<Drink>)
                    }
                }
                is DrinksResult.Error -> {
                    ScreenState.Error(response.message)
                }
            }
        }
    }
}

val cocktailModelFactory = viewModelFactory {
    initializer {
        CocktailViewModel()
    }
}