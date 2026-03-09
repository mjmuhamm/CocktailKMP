package com.example.cocktailapi.model

import kotlinx.serialization.Serializable

@Serializable
data class CocktailResponse(
    val drinks: List<Drink>?
)