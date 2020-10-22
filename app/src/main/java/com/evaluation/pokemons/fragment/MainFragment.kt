package com.evaluation.pokemons.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.evaluation.R
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.databinding.MainLayoutBinding
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.pokemons.viewmodel.PokemonViewModel
import com.evaluation.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author Vladyslav Havrylenko
 * @since 09.03.2020
 */
@AndroidEntryPoint
class MainFragment : Fragment(), AdapterItemClickListener<BaseItemView> {

    private var binding: MainLayoutBinding by autoCleared()

    private val viewModel: PokemonViewModel by viewModels()

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

    private fun initRootView() {
        binding.listView.listener = this
    }

    private fun initLoader() {
        viewModel.items.observe(viewLifecycleOwner, binding.listView.adapter::submitList)
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