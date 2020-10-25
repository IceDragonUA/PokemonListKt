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
import com.evaluation.fragment.BaseFragment
import com.evaluation.R
import com.evaluation.utils.LANGUAGE
import com.evaluation.utils.empty
import com.evaluation.viewmodel.LanguageViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var language: String = empty()

    private var lastLanguage : String? = null

    private val viewModel: LanguageViewModel by viewModels()

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
        viewModel.langResult.observe(this) { languages ->
            language = if (lastLanguage != null) lastLanguage as String else languages.first().name
            val drawerMenu: Menu = navLanguage.menu
            languages.forEach {
                drawerMenu.add(it.name)
            }
            navLanguage.setNavigationItemSelectedListener(this)
            if (lastLanguage != language) fragment()?.languageLoaded(language)
            lastLanguage = language
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        language = item.title.toString()
        if (lastLanguage != language) fragment()?.languageSwitched(language)
        lastLanguage = language
        drawer.closeDrawers()
        return true
    }

    private fun fragment(): BaseFragment? {
        val fragments = navHostFragment.childFragmentManager.fragments
        return fragments.find { it is BaseFragment && it.isVisible } as? BaseFragment
    }
}