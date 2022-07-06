package com.example.pokedex.repository

import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.data.remote.response.Pokemon
import com.example.pokedex.data.remote.response.PokemonList
import com.example.pokedex.util.DataResult
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonNetworkDatasource @Inject constructor(private val api: PokemonApi) {

    suspend fun getPokemonList(limit: Int, offset: Int): DataResult<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return DataResult.Exception("unknown error")
        }
        return DataResult.Success(response)
    }

    suspend fun getPokemonDetail(name: String): DataResult<Pokemon> {
        val response = try {
            api.getPokemon(name)
        } catch (e: Exception) {
            return DataResult.Exception("unknown error")
        }
        return DataResult.Success(response)
    }

}