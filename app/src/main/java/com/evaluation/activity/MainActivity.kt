package com.evaluation.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.evaluation.R
import com.evaluation.fragment.BaseFragment
import com.evaluation.utils.LANGUAGE
import com.evaluation.utils.emptyCategory
import com.evaluation.utils.emptyString
import com.evaluation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var language: String = emptyString()
    private var lastLanguage : String? = null

    private val viewModel: SettingsViewModel by viewModels()

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        setupWithNavController(toolBar, navController, drawer)
        restoreInstance(savedInstanceState)
    }

    private fun restoreInstance(savedInstanceState: Bundle?) {
        val language = savedInstanceState?.getString(LANGUAGE)
        if (language != null) {
            lastLanguage = language
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        lastLanguage = language
        if (lastLanguage != null) {
            outState.putString(LANGUAGE, lastLanguage)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        initLoader()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment)) ||
                super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        when {
            drawer.isDrawerOpen(GravityCompat.START) -> {
                drawer.closeDrawer(GravityCompat.START)
            }
            drawer.isDrawerOpen(GravityCompat.END) -> {
                drawer.closeDrawer(GravityCompat.END)
            }
            else -> {
                return super.onBackPressed()
            }
        }
    }

    private fun initLoader() {
        initLangLoader()
        initCategoryLoader()
    }

    private fun initLangLoader() {
        viewModel.langResult.observe(this) { languages ->
            language = if (lastLanguage != null) lastLanguage as String else languages.first().name
            val drawerMenu: Menu = navLanguage.menu
            languages.forEach {
                drawerMenu.add(it.name)
            }
            navLanguage.setNavigationItemSelectedListener {
                language = it.title.toString()
                if (lastLanguage != language) fragment()?.languageSwitched(language)
                lastLanguage = language
                drawer.closeDrawers()
                true
            }
            if (lastLanguage != language) fragment()?.languageLoaded(language)
            lastLanguage = language
        }
    }

    private fun initCategoryLoader() {
        viewModel.categoryResult.observe(this) { categories ->
            val drawerMenu: Menu = navCategory.menu
            categories.forEach {
                drawerMenu.add(it.name)
            }
            navCategory.setNavigationItemSelectedListener { menu ->
                fragment()?.categorySwitched(categories.find { it.name == menu.title.toString() } ?: emptyCategory())
                drawer.closeDrawers()
                true
            }
        }
    }

    private fun fragment(): BaseFragment? {
        val fragments = navHostFragment.childFragmentManager.fragments
        return fragments.find { it is BaseFragment && it.isVisible } as? BaseFragment
    }
}