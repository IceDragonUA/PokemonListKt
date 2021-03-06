package com.evaluation.pokemons.dagger

import android.content.Context
import androidx.paging.PagedList
import com.evaluation.database.AppDatabase
import com.evaluation.executor.BaseExecutor
import com.evaluation.executor.ThreadExecutor
import com.evaluation.network.RestApi
import com.evaluation.network.handler.NetworkHandler
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.datasource.AppPokemonDataSource
import com.evaluation.pokemons.datasource.AppPokemonDataSourceFactory
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.pokemons.interaction.AppPokemonsInteractionImpl
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.pokemons.network.AppPokemonsRestApiDaoImpl
import com.evaluation.pokemons.repository.AppPokemonsRepository
import com.google.gson.Gson
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
    fun appRest(appRest: RestApi, appDatabase: AppPokemonsDatabaseDao, handler: NetworkHandler, mapper: PokemonMapper): AppPokemonsRestApiDao =
        AppPokemonsRestApiDaoImpl(appRest, appDatabase, handler, mapper)

    @Provides
    @Singleton
    fun appDao(appDatabase: AppDatabase): AppPokemonsDatabaseDao = appDatabase.appListDao()

    @Singleton
    @Provides
    fun appRepository(context: Context, mapper: PokemonMapper, remoteDao: AppPokemonsRestApiDao, localDao: AppPokemonsDatabaseDao, executor: BaseExecutor, gson: Gson) =
        AppPokemonsRepository(context, mapper, remoteDao, localDao, executor, gson)

    @Singleton
    @Provides
    fun appDataSource(appRepository: AppPokemonsRepository) = AppPokemonDataSource(appRepository)

    @Singleton
    @Provides
    fun appDataSourceFactory(appDataSource: AppPokemonDataSource) = AppPokemonDataSourceFactory(appDataSource)

    @Singleton
    @Provides
    fun appInteraction(factory: AppPokemonDataSourceFactory, config: PagedList.Config, networkExecutor: Executor, repository: AppPokemonsRepository): AppPokemonsInteraction =
        AppPokemonsInteractionImpl(factory, config, networkExecutor, repository)

}