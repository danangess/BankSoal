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
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.databinding.NavHeaderMainBinding
import com.example.banksoal.ui.about.AboutFragment
import com.example.banksoal.ui.course.CourseFragment
import com.example.banksoal.ui.main.fragment.MainFragment
import com.example.banksoal.ui.user_guide.UserGuideFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator, HasSupportFragmentInjector {

    companion object {
        const val TAG = "Main"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private lateinit var mMainViewModel: MainViewModel
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainViewModel.setNavigator(this)
        mMainViewModel.loadUserData()
//        loadBackground()
        showMainFragment()
        setupNavMenu()
        mMainViewModel.isLoading.set(false)
//        loadProfilePicture()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG)
        if (fragment == null) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Back again to exit", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
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

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): MainViewModel {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel::class.java)
        return mMainViewModel
    }

    override fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun openLoginActivity() {
        val intent = LoginActivity.newIntent(this@MainActivity)
        startActivity(intent)
        finish()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

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
                R.id.navItemMain -> {
                    showMainFragment()
                    true
                }
                R.id.navItemCourse -> {
                    showCourseFragment()
                    true
                }
                R.id.navItemUserGuid -> {
                    showUserGuideFragment()
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

    private fun loadProfilePicture() {
        try {
            val ppImageView = findViewById<ImageView>(R.id.iv_profile_pic)
            ppImageView.visibility = android.view.View.VISIBLE
            val baseImageLocation = "images/"
            val fileName = "iconloading.jpg"
            val location = baseImageLocation + fileName
            val ims = this.assets.open(location)
            val drawable = Drawable.createFromStream(ims, null)
            ppImageView.setImageDrawable(drawable)
        } catch (ex: Exception) {
            Log.e(TAG, ex.message)
        }
    }

    private fun loadBackground() {
        try {
            val frameLayout = findViewById<FrameLayout>(R.id.contentFragment)
            val baseImageLocation = "images/"
            val fileName = "iconloading.jpg"
            val location = baseImageLocation + fileName
            val ims = this.assets.open(location)
            val drawable = Drawable.createFromStream(ims, null)
            frameLayout.background = drawable
            frameLayout.setBackgroundDrawable(drawable)
        } catch (ex: Exception) {
            Log.e(TAG, ex.message)
        }
    }

    override fun showMainFragment() {
        unlockDrawer()
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.contentFragment, MainFragment.newFragment(), MainFragment.TAG)
            .commit()
    }

    override fun showCourseFragment() {
        unlockDrawer()
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.contentFragment, CourseFragment.newFragment(), CourseFragment.TAG)
            .commit()
    }

//    override fun showCompetencesFragment() {
//        unlockDrawer()
//        supportFragmentManager
//            .beginTransaction()
//            .disallowAddToBackStack()
//            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//            .add(R.id.clRootView, CompetencesFragment.newFragment(), CompetencesFragment.TAG)
//            .commit()
//    }

    override fun showUserGuideFragment() {
        unlockDrawer()
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.contentFragment, UserGuideFragment.newFragment(), UserGuideFragment.TAG)
            .commit()
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

    private fun lockDrawer() {
        viewDataBinding.drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun unlockDrawer() {
        viewDataBinding.drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
}