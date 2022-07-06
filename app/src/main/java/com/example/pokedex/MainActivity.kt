package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.ui.home.PokemonListScreen
import com.example.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list") {
                    composable(
                        route = "pokemon_list"
                    ) {
                        PokemonListScreen(navController = navController)
                    }
                    composable(
                        route = "pokemon_detail/name/{color}",
                        arguments = listOf(
                            navArgument("color") {
                                type = NavType.IntType
                            },
                            navArgument("name") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val colorArgs = remember {
                            val color = it.arguments?.getInt("color")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val nameArgs = remember {
                            val name = it.arguments?.getString("name")
                        }
                    }
                }
            }
        }
    }
}