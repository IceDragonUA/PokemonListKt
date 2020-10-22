package com.evaluation.tests.dao.network

import com.evaluation.executor.TestExecutor
import com.evaluation.network.RestApi
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.pokemons.network.AppPokemonsRestApiDaoImpl
import com.evaluation.tests.dao.RetrofitMocks
import com.evaluation.tests.test
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


/**
 * @author Vladyslav Havrylenko
 * @since 11.10.2020
 */
class AppProgramsRestApiDaoTest {

    private lateinit var appProgramsRestApiDao: AppPokemonsRestApiDao

    private var appRest: RestApi = RetrofitMocks.appRest

    @Mock
    private lateinit var executor: TestExecutor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        appProgramsRestApiDao = AppPokemonsRestApiDaoImpl(appRest, executor)
        whenever(executor.mainExecutor).thenReturn(Schedulers.trampoline())
        whenever(executor.postExecutor).thenReturn(Schedulers.trampoline())
    }

    @Test
    fun `should do call`() {
        assertNotNull(appRest)
        assertNotNull(executor)
        assertNotNull(appProgramsRestApiDao)

        appProgramsRestApiDao.pokemonList(0, 0).test {
            assertValueCount(1)
            assertNoErrors()
            assertComplete()
        }
    }
}