package com.example.cocktailkmp.repository

import com.example.cocktailapi.model.CocktailResponse
import com.example.cocktailapi.model.Drink
import com.example.cocktailapi.states.DrinksResult

interface CocktailRepository {
    suspend fun getCocktailsByName(query: String): DrinksResult<List<Drink>>
}