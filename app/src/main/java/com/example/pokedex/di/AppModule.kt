package com.example.pokedex.di

import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.repository.PokemonNetworkDatasource
import com.example.pokedex.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class AppModule {

    @Singleton
    @Provides
    fun providePokemonDatasource(
        api: PokemonApi
    ) = PokemonNetworkDatasource(api)

    fun providePokemonApi(): PokemonApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(PokemonApi::class.java)
    }

}