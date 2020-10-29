package com.evaluation.tests.dao.network

import android.content.Context
import com.evaluation.network.RestApi
import com.evaluation.network.handler.NetworkHandler
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.pokemons.network.AppPokemonsRestApiDaoImpl
import com.evaluation.tests.dao.RetrofitMocks
import com.evaluation.tests.dao.RoomMocks
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


/**
 * @author Vladyslav Havrylenko
 * @since 11.10.2020
 */
class AppPokemonsRestApiDaoTest {

    @Mock
    private lateinit var context: Context

    private lateinit var appPokemonsRestApiDao: AppPokemonsRestApiDao

    private var appRest: RestApi = RetrofitMocks.appRest

    private lateinit var appDatabaseDao: AppPokemonsDatabaseDao

    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var mapper: PokemonMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        appDatabaseDao = RoomMocks.appDatabase(context).appListDao()
        appPokemonsRestApiDao = AppPokemonsRestApiDaoImpl(
            appRest,
            appDatabaseDao,
            networkHandler,
            mapper
        )
    }

    @Test
    fun `should do call`() {
        assertNotNull(appRest)
        assertNotNull(appDatabaseDao)
        assertNotNull(networkHandler)
        assertNotNull(mapper)
        assertNotNull(appPokemonsRestApiDao)
        whenever(appPokemonsRestApiDao.pokemonList(0, 20)).thenReturn(Completable.complete())
        verify(appPokemonsRestApiDao).pokemonList(0, 20)
    }
}