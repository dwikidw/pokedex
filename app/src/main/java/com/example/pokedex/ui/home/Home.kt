package com.example.pokedex.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.ui.widget.SearchBar
import coil.decode.SvgDecoder
import com.example.pokedex.data.model.PokemonAbilty
import com.example.pokedex.data.model.PokemonItem
import com.example.pokedex.ui.widget.FilterCheckBox
import java.util.*


@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        var pokemon by remember { viewModel.pokemon }

        Column(
            horizontalAlignment = CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_pokemon_logo),
                contentDescription = "pokeball_logo",
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            SearchBar(
                hint = "Search", modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 24.dp, 24.dp, 4.dp),
                onType = {
                    viewModel.onSearching(it)
                },
                onSearch = {
                    viewModel.onClickSearch()
                }
            )
            FilterCheckBox()
            PokemonSection(
                pokemon = pokemon, modifier = Modifier
                    .padding(24.dp)
                    .fillMaxHeight(1f)
            )
        }
    }
}

@Composable
fun PokemonSection(
    pokemon: PokemonItem,
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel()

) {
    val listAbs = viewModel.listAbilty.collectAsState()
    var searchFilter by remember { viewModel.searchFilter }
    var ability by remember { viewModel.abilty }
    var isLoading by remember { viewModel.isLoading }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .shadow(5.dp, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp)),
        contentAlignment = if (pokemon.imgUrl.isEmpty()) Alignment.Center else Alignment.TopCenter,
    ) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = pokemon.imgUrl)
                .placeholder(R.drawable.ic_vector_pokeball)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(500)
                .build()
        )
        if (isLoading) {
            Image(
                painter = painterResource(id = R.drawable.ic_vector_pokeball),
                contentDescription = "placeholder",
                modifier = Modifier.fillMaxSize(0.5f)
            )
        }

        when (searchFilter) {
            HomeViewModel.SearchingFilter.NAME -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = pokemon.name.uppercase(),
                        fontSize = 24.sp
                    )
                    Text(
                        text = pokemon.type.capitalize(Locale.getDefault()),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painter,
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        itemsIndexed(listAbs.value) { _, item ->
                            DescriptionPokemon(
                                ability = item,
                                modifier = Modifier.padding(16.dp, 8.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            HomeViewModel.SearchingFilter.ABILITY -> {
                if (ability.name.isNotEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = ability.name.uppercase(),
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        val list =
                            listOf(
                                "Short Effect" to ability.shortEffect,
                                "Effect" to ability.effect
                            )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            itemsIndexed(list) { _, item ->
                                DescriptionAbility(
                                    ability = item,
                                    modifier = Modifier.padding(16.dp, 8.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DescriptionAbility(
    ability: Pair<String, String>,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally,
    ) {
        Text(
            text = ability.first,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = ability.second,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
fun DescriptionPokemon(
    ability: PokemonAbilty,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally,
    ) {
        Text(
            text = "Ability",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = ability.name.uppercase(),
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Short Effect",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = ability.shortEffect,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Effect",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = ability.effect,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
