package com.example.banksoal.ui.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import com.example.banksoal.ui.base.BaseActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.widget.Toast
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.databinding.ActivityLoginBinding
import com.example.banksoal.ui.main.MainActivity
import com.example.banksoal.ui.signup.SignupFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator, HasSupportFragmentInjector {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private lateinit var mLoginViewModel: LoginViewModel
//    private lateinit var mActivityLoginBinding: ActivityLoginBinding

//    private val mDrawer: DrawerLayout? = null

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): LoginViewModel {
        mLoginViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel::class.java)
        return mLoginViewModel
    }

    override fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
        // handle error
    }

    override fun login() {
        val username = viewDataBinding.etUsername.text.toString()
        val password = viewDataBinding.etPassword.text.toString()
        if (mLoginViewModel.isUsernameAndPasswordValid(username, password)) {
            hideKeyboard()
            mLoginViewModel.doLogin(username, password)
        } else {
            Toast.makeText(this, getString(R.string.invalid_username_password), Toast.LENGTH_SHORT).show()
        }
    }

    override fun openMainActivity() {
        val intent = MainActivity.newIntent(this@LoginActivity)
        startActivity(intent)
        finish()
    }

    override fun openSignUpFragment() {
        lockDrawer()
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(R.id.clRootView, SignupFragment.newFragment(), SignupFragment.TAG)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mActivityLoginBinding = getViewDataBinding()
        mLoginViewModel.setNavigator(this)
//        setupNavMenu()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(SignupFragment.TAG)
        if (fragment == null) {
            super.onBackPressed()
        } else {
            onFragmentDetached(SignupFragment.TAG)
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

    override fun onResume() {
        super.onResume()
        viewDataBinding.drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
//
//    private fun setupNavMenu() {
//        val navHeaderMainBinding = DataBindingUtil.inflate<NavHeaderMainBinding>(layoutInflater, R.layout.nav_header_main, getViewDataBinding().navigationView, false)
//        getViewDataBinding().navigationView.addHeaderView(navHeaderMainBinding.root)
//        navHeaderMainBinding.viewModel = mLoginViewModel
//
////        getViewDataBinding().navigationView.setNavigationItemSelectedListener()
//    }

    private fun lockDrawer() {
        viewDataBinding.drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun unlockDrawer() {
        viewDataBinding.drawerView.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
}