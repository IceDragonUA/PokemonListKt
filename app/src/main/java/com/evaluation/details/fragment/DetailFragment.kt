package com.evaluation.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.evaluation.R
import com.evaluation.utils.defIfNull
import com.evaluation.utils.initText
import com.evaluation.utils.loadFromUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_layout.*

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRootView(DetailFragmentArgs.fromBundle(requireArguments()))
    }

    private fun initRootView(fromBundle: DetailFragmentArgs) {
        image.loadFromUrl(fromBundle.item.front_default)

        weight_value.initText(fromBundle.item.weight.toString())
        height_value.initText(fromBundle.item.height.toString())
        experience_value.initText(fromBundle.item.experience.toString())

        stats_value.initText(fromBundle.item.stats.joinToString { it.name })
        abilities_value.initText(fromBundle.item.abilities.joinToString { it.name })
        types_value.initText(fromBundle.item.types.joinToString { it.name })
    }

}