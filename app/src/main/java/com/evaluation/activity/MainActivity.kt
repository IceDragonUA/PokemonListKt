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
import com.evaluation.BaseFragment
import com.evaluation.R
import com.evaluation.viewmodel.LanguageViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            return super.onBackPressed()
        }
    }

    private fun initLoader() {
        viewModel.langResult.observe(this) { languages ->
            val drawerMenu: Menu = navigation.menu
            languages.forEach {
                drawerMenu.add(it.name)
            }
            navigation.setNavigationItemSelectedListener(this)
            fragment().languageLoaded(languages.first().name)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        fragment().languageSwitched(item.title.toString())
        drawer.closeDrawers()
        return true
    }

    private fun fragment(): BaseFragment {
        val fragments = navHostFragment.childFragmentManager.fragments
        return fragments.find { it is BaseFragment && it.isVisible } as BaseFragment
    }
}