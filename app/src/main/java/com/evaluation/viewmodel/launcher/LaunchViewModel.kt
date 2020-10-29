package com.evaluation.viewmodel.launcher

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.evaluation.utils.LauncherViewState
import com.evaluation.pokemons.interaction.AppPokemonsInteraction

/**
 * @author Vladyslav Havrylenko
 * @since 24.10.2020
 */
class LaunchViewModel @ViewModelInject constructor(
    private val interaction: AppPokemonsInteraction
) : ViewModel() {

    private var _load: LiveData<LauncherViewState>? = null
    val load: LiveData<LauncherViewState>
        get() {
            if (_load == null) {
                _load = LiveDataReactiveStreams.fromPublisher(interaction.load())
            }
            return _load ?: throw AssertionError("Set to null by another thread")
        }
}