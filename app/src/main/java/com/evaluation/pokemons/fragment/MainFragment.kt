package com.evaluation.pokemons.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.evaluation.BaseFragment
import com.evaluation.R
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.databinding.MainLayoutBinding
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.pokemons.viewmodel.PokemonViewModel
import com.evaluation.utils.ICONIFIED
import com.evaluation.utils.QUERY
import com.evaluation.utils.autoCleared
import com.evaluation.utils.empty
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
@AndroidEntryPoint
class MainFragment : BaseFragment(), AdapterItemClickListener<BaseItemView>, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private var binding: MainLayoutBinding by autoCleared()

    private val viewModel: PokemonViewModel by viewModels()

    private var language: String = empty()

    private var queryTextChangedJob: Job? = null

    private var lastSearchQuery: String? = null

    private var isIconified: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        restoreInstance(savedInstanceState)
    }

    private fun restoreInstance(savedInstanceState: Bundle?) {
        val query = savedInstanceState?.getString(QUERY)
        if (query != null) {
            lastSearchQuery = query
        }
        val iconified = savedInstanceState?.getBoolean(ICONIFIED)
        if (iconified != null) {
            isIconified = iconified
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (lastSearchQuery != null) {
            outState.putString(QUERY, lastSearchQuery)
        }
        outState.putBoolean(ICONIFIED, isIconified)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_layout, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRootView()
        initLoader()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        initSearchView(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initSearchView(menu: Menu) {
        val menuItem = menu.findItem(R.id.search)
        menuItem.setOnActionExpandListener(this)
        val search = menuItem?.actionView as? SearchView
        search?.maxWidth = Integer.MAX_VALUE
        search?.queryHint = getString(R.string.search)
        search?.setOnQueryTextListener(this)
        if (!isIconified) {
            menuItem.expandActionView()
        }
        if (lastSearchQuery != null) {
            search?.setQuery(lastSearchQuery, false)
        }
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        isIconified = false
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        viewModel.search(Pair(empty(), language))
        isIconified = true
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(query: String?): Boolean {
        queryTextChangedJob?.cancel()
        if (isAdded) {
            queryTextChangedJob = GlobalScope.launch(Dispatchers.Main) {
                delay(500)
                if (!query.isNullOrEmpty()) {
                    if (lastSearchQuery != query) {
                        viewModel.search(Pair(query, "en"))
                    }
                }
                lastSearchQuery = query
            }
        }
        return false
    }

    private fun initRootView() {
        binding.listView.listener = this
    }

    private fun initLoader() {
        viewModel.items.observe(viewLifecycleOwner, binding.listView.adapter::submitList)
    }

    override fun languageLoaded(language: String) {
        this.language = language
        viewModel.search(Pair(empty(), language))
    }

    override fun languageSwitched(language: String) {
        this.language = language
        lastSearchQuery?.let {
            viewModel.search(Pair(it, language))
        } ?: viewModel.search(Pair(empty(), language))
    }

    override fun onClicked(item: BaseItemView) {
        when (item) {
            is CardItemView -> findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(
                    item.viewItem.name,
                    item.viewItem
                )
            )
        }
    }

}