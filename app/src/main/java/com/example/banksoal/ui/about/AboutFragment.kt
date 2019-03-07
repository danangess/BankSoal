package com.example.banksoal.ui.about

import android.os.Bundle
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.ui.base.BaseFragment
import com.example.banksoal.databinding.FragmentAboutBinding
import javax.inject.Inject

class AboutFragment : BaseFragment<FragmentAboutBinding, AboutViewModel>(), AboutNavigator {
    @Inject
    lateinit var mAboutViewModel: AboutViewModel

    companion object {
        const val TAG: String = "Main/About"

        fun newFragment(): AboutFragment {
            val args = Bundle()
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_about
    }

    override fun getViewModel(): AboutViewModel {
        return mAboutViewModel
    }

    override fun goBack() {
        activity?.onFragmentDetached(TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAboutViewModel.setNavigator(this)
    }
}