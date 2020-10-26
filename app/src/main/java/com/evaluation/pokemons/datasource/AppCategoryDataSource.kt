package com.evaluation.pokemons.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.utils.NetworkState
import com.evaluation.utils.emptyCategory
import com.evaluation.utils.emptyString
import javax.inject.Inject


/**
 * @author Vladyslav Havrylenko
 * @since 08.10.2020
 */
class AppCategoryDataSource @Inject constructor() : PageKeyedDataSource<Int, BaseItemView>() {

    var query = emptyString()
    var category = emptyCategory()
    val network = MutableLiveData<Boolean>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BaseItemView>) {
        postInitialState(NetworkState.LOADING)
        val list = category.pokemons
            .filter { query == emptyString() || it.name.startsWith(query) }
            .map {
                CardItemView(
                    index = emptyString(),
                    next = null,
                    viewItem = it
                )
            }
        postInitialState(NetworkState.LOADED)
        callback.onResult(list, null, null)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BaseItemView>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BaseItemView>) {

    }

    private fun postInitialState(networkState: NetworkState) {
        network.postValue(networkState.value())
    }

}
