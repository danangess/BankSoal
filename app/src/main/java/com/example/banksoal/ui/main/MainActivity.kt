package com.example.banksoal.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.example.banksoal.databinding.ActivityMainBinding
import com.example.banksoal.ui.base.BaseActivity
import com.example.banksoal.ui.login.LoginActivity
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import android.support.v4.view.GravityCompat
import android.databinding.DataBindingUtil
import android.os.Handler
import android.support.v4.widget.DrawerLayout
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.databinding.NavHeaderMainBinding
import com.example.banksoal.ui.about.AboutFragment


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator, HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private lateinit var mMainViewModel: MainViewModel
    private var doubleBackToExitPressedOnce = false

//    private lateinit var mActivityMainBinding: ActivityMainBinding

    fun newIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mActivityMainBinding = getViewDataBinding()
        mMainViewModel.setNavigator(this)
        setupNavMenu()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG)
        if (fragment == null) {
            if (doubleBackToExitPressedOnce){
                super.onBackPressed()
                return
            }
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Back again to exit", Toast.LENGTH_SHORT).show()
            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        } else {
            onFragmentDetached(AboutFragment.TAG)
        }
    }

    override fun onFragmentDetached(tag: String) {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            fragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .remove(fragment)
                .commitNow()
            unlockDrawer()
        }
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel::class.java)
        return mMainViewModel
    }

    override fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun openLoginActivity() {
        val intent = LoginActivity().newIntent(this@MainActivity)
        startActivity(intent)
        finish()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    private fun setupNavMenu() {
        val navHeaderMainBinding = DataBindingUtil.inflate<NavHeaderMainBinding>(
            layoutInflater,
            R.layout.nav_header_main, viewDataBinding.navigationView, false
        )
        viewDataBinding.navigationView.addHeaderView(navHeaderMainBinding.root)
        navHeaderMainBinding.viewModel = mMainViewModel

        viewDataBinding.navigationView.setNavigationItemSelectedListener { item ->
            viewDataBinding.drawerView.closeDrawer(GravityCompat.START)
            when (item.itemId) {
                R.id.navItemQuiz -> {
                    openQuizActivity()
                    true
                }
                R.id.navItemAbout -> {
                    showAboutFragment()
                    true
                }
                R.id.navItemLogout -> {
                    mMainViewModel.logout()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun showAboutFragment() {
        lockDrawer()
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(R.id.clRootView, AboutFragment.newFragment(), AboutFragment.TAG)
            .commit()
    }

    override fun openQuizActivity() {
//        val intent = QuizActivity().newIntent(this@MainActivity)
//        startActivity(intent)
//        finish()
        Toast.makeText(this, "Quizes", Toast.LENGTH_SHORT).show()
    }

    private fun lockDrawer() {
        viewDataBinding.drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun unlockDrawer() {
        viewDataBinding.drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
}