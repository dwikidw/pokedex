package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                val navControl = rememberNavController()
                NavHost(navController = navControl, startDestination = "pokemon_list") {
                    composable(
                        route = "pokemon_list"
                    ) {

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