package com.evaluation.pokemons.dagger

import android.content.Context
import androidx.paging.PagedList
import com.evaluation.database.AppDatabase
import com.evaluation.executor.BaseExecutor
import com.evaluation.network.RestApi
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.datasource.AppCategoryDataSource
import com.evaluation.pokemons.datasource.AppCategoryDataSourceFactory
import com.evaluation.pokemons.datasource.AppPokemonDataSource
import com.evaluation.pokemons.datasource.AppPokemonDataSourceFactory
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.pokemons.interaction.AppPokemonsInteractionImpl
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.pokemons.network.AppPokemonsRestApiDaoImpl
import com.evaluation.pokemons.repository.AppPokemonsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataPokemonsModule {

    @Singleton
    @Provides
    fun appRest(appRest: RestApi, executor: BaseExecutor): AppPokemonsRestApiDao = AppPokemonsRestApiDaoImpl(appRest, executor)

    @Provides
    @Singleton
    fun appDao(appDatabase: AppDatabase): AppPokemonsDatabaseDao = appDatabase.appListDao()

    @Singleton
    @Provides
    fun appRepository(context: Context, mapper: PokemonMapper, remoteDao: AppPokemonsRestApiDao, localDao: AppPokemonsDatabaseDao) =
        AppPokemonsRepository(context, mapper, remoteDao, localDao)

    @Singleton
    @Provides
    fun appPokemonDataSource(appRepository: AppPokemonsRepository) = AppPokemonDataSource(appRepository)

    @Singleton
    @Provides
    fun appPokemonDataSourceFactory(appDataSource: AppPokemonDataSource) = AppPokemonDataSourceFactory(appDataSource)

    @Singleton
    @Provides
    fun appCategoryDataSource() = AppCategoryDataSource()

    @Singleton
    @Provides
    fun appCategoryDataSourceFactory(appDataSource: AppCategoryDataSource) = AppCategoryDataSourceFactory(appDataSource)

    @Singleton
    @Provides
    fun appInteraction(pokemonFactory: AppPokemonDataSourceFactory, categoryFactory: AppCategoryDataSourceFactory, config: PagedList.Config, networkExecutor: Executor, repository: AppPokemonsRepository): AppPokemonsInteraction =
        AppPokemonsInteractionImpl(pokemonFactory, categoryFactory, config, networkExecutor, repository)

}