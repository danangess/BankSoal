package com.example.banksoal.ui.main.fragment

import android.os.Bundle
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.databinding.FragmentMainBinding
import com.example.banksoal.ui.base.BaseFragment
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>(), MainFragmentNavigator {

    companion object {
        val TAG: String = "Main/Main"

        fun newFragment(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var mMainFragmentViewModel: MainFragmentViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun getViewModel(): MainFragmentViewModel = mMainFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainFragmentViewModel.setNavigator(this)

        mMainFragmentViewModel.isLoading.set(false)
    }
}