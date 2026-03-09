package com.example.cocktailapi.states

import com.example.cocktailapi.model.CocktailResponse
import com.example.cocktailapi.model.Drink

// loading, error, success states
sealed class DrinksResult<out T> {
    data class Success<T>(val data: T): DrinksResult<T>()
    data class Error(val message: String) : DrinksResult<Nothing>()


}

sealed class ScreenState {
    data object Nothing : ScreenState()
    data object Loading : ScreenState()
    data class Error(val msg: String) : ScreenState()
    data class Success(val drinks: List<Drink>) : ScreenState()
}