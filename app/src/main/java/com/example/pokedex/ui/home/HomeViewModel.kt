package com.example.pokedex.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.PokemonAbilty
import com.example.pokedex.data.model.PokemonItem
import com.example.pokedex.repository.PokemonNetworkDatasource
import com.example.pokedex.util.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: PokemonNetworkDatasource) :
    ViewModel() {

    val dummy = PokemonItem("", "", "", emptyList())
    var pokemon = mutableStateOf(dummy)
    var queryPokemon = mutableStateOf("")
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var searchFilter = mutableStateOf(SearchingFilter.UNDEFINED)
    private val _listAbility = MutableStateFlow(listOf<PokemonAbilty>())
    val listAbilty: StateFlow<List<PokemonAbilty>> = _listAbility

    fun onSearching(name: String) {
        queryPokemon.value = name.lowercase()
        if (name.isEmpty()) {
            pokemon.value = dummy
        }
    }


    fun onClickSearch() {
        if (queryPokemon.value.isNotEmpty()) {
            isLoading.value = true
            viewModelScope.launch {
                when (val result = repository.getPokemonDetail(queryPokemon.value)) {
                    is DataResult.Exception -> {
                        loadError.value = result.message.toString()
                    }
                    is DataResult.Loading -> {
                        isLoading.value = true
                    }
                    is DataResult.Success -> {
                        result.data?.description?.let { _listAbility.emit(it) }
                        pokemon.value = PokemonItem(
                            name = result.data?.name.toString(),
                            imgUrl = result.data?.imgUrl.toString(),
                            type = result.data?.type.toString(),
                            description = listAbilty.value
                        )
                        searchFilter.value = SearchingFilter.NAME
                        isLoading.value = false
                    }
                }
            }
        }
    }

    enum class SearchingFilter {
        NAME, ABILITY, UNDEFINED
    }
}