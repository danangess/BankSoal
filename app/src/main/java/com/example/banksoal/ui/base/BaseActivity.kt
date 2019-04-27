package com.example.banksoal.ui.base

import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.databinding.DataBindingUtil
import android.os.Build
import android.annotation.TargetApi
import dagger.android.AndroidInjection
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.app.ProgressDialog
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.example.banksoal.ext.showLoadingDialog
import com.example.banksoal.ui.login.LoginActivity
import com.example.banksoal.utils.NetworkUtils

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseFragment.Callback {
    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private var mProgressDialog: ProgressDialog? = null
    private var mViewModel: V? = null
    lateinit var viewDataBinding: T

    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog?.cancel()
        }
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

    fun openActivityOnTokenExpire() {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    fun showLoading() {
        hideLoading()
        mProgressDialog = this.showLoadingDialog()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        if (mViewModel == null) {
            mViewModel = getViewModel()
        }
        viewDataBinding.setVariable(getBindingVariable(), mViewModel)
        viewDataBinding.executePendingBindings()
    }
}