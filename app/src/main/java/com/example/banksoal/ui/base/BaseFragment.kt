package com.example.banksoal.ui.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection
import android.os.Bundle
import android.databinding.DataBindingUtil
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.View

abstract class BaseFragment<T: ViewDataBinding, V: BaseViewModel<*>> : Fragment() {
    var activity: BaseActivity<*, *>? = null
    private lateinit var mRootView: View
    private var mViewModel: V? = null
    lateinit var viewDataBinding: T

    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.activity = context
            context.onFragmentAttached()
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = viewDataBinding.root
        return mRootView
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    override fun onViewCreated(@NonNull view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.setVariable(getBindingVariable(), mViewModel)
        viewDataBinding.executePendingBindings()
    }

    fun hideKeyboard() {
        activity?.hideKeyboard()
    }

    fun isNetworkConnected(): Boolean {
        return activity != null && activity!!.isNetworkConnected()
    }

    fun openActivityOnTokenExpire() {
        activity?.openActivityOnTokenExpire()
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    interface Callback {
        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}