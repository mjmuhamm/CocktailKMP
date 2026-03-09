package com.example.cocktailkmp.repository

import com.example.cocktailapi.model.CocktailResponse
import com.example.cocktailapi.model.Drink
import com.example.cocktailapi.remote.Network
import com.example.cocktailapi.states.DrinksResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType

const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

class CocktailRepositoryImpl(private val client : HttpClient) : CocktailRepository {
    override suspend fun getCocktailsByName(query: String): DrinksResult<List<Drink>> {
        try {
            val response : CocktailResponse = client.get("${BASE_URL}search.php") {
                parameter("s", query)
                accept(ContentType.Application.Json)
            }.body()

            val drinks = response.drinks

            return DrinksResult.Success(drinks) as DrinksResult<List<Drink>>
        } catch (e: Exception) {
            return DrinksResult.Error(e.message ?: "Unknown error")

        }

    }
}