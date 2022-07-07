package com.example.pokedex.data.model

data class PokemonItem(
    val name: String,
    val imgUrl: String,
    val type: String,
    val description : List<PokemonAbilty>
)

data class PokemonAbilty(
    val name : String,
    val shortEffect : String,
    val effect: String,
)