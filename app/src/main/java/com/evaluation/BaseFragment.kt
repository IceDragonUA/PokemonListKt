package com.evaluation

import androidx.fragment.app.Fragment

/**
 * @author Vladyslav Havrylenko
 * @since 24.10.2020
 */
abstract class BaseFragment : Fragment() {

    abstract fun languageLoaded(language: String)

    abstract fun languageSwitched(language: String)

}