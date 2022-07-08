package com.example.pokedex.repository

import com.example.pokedex.data.model.PokemonAbilty
import com.example.pokedex.data.model.PokemonItem
import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.data.remote.response.Ability
import com.example.pokedex.data.remote.response.AbilityPokemon
import com.example.pokedex.data.remote.response.Pokemon
import com.example.pokedex.data.remote.response.PokemonList
import com.example.pokedex.util.DataResult
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class PokemonNetworkDatasource @Inject constructor(private val api: PokemonApi) {

    suspend fun getPokemonList(limit: Int, offset: Int): DataResult<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return DataResult.Exception("$e")
        }
        return DataResult.Success(response)
    }

    suspend fun getPokemonDetail(name: String): DataResult<PokemonItem> {
        val response = try {
            api.getPokemon(name)
        } catch (e: Exception) {
            return DataResult.Exception("$e")
        }
        return DataResult.Success(
            PokemonItem(
                name = response.name,
                imgUrl = response.sprites.other.dream_world.front_default,
                type = response.types[0].type.name,
                description = response.abilities.map {
                    getPokemonAbility(it.ability.name)
                }.toList()
            )

        )
    }

    suspend fun getPokemonAbility(ability: String): PokemonAbilty {
        val response = try {
            api.getPokemonAbility(ability)
        } catch (e: Exception) {
            return PokemonAbilty("", "", "")
        }
        val effectResponse = response.effect_entries.filter { it.language.name == "en" }.toList()
        return PokemonAbilty(
            name = response.name,
            effect = effectResponse.first().effect,
            shortEffect = effectResponse.first().short_effect
        )
    }

}