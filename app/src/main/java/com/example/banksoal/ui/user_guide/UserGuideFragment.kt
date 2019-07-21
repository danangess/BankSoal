package com.example.banksoal.ui.user_guide

import android.os.Bundle
import android.widget.Toast
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.databinding.FragmentUserGuideBinding
import com.example.banksoal.ui.base.BaseFragment
import javax.inject.Inject

class UserGuideFragment : BaseFragment<FragmentUserGuideBinding, UserGuideViewModel>(), UserGuideNavigator {

    companion object {
        const val TAG: String = "Main/UserGuide"

        fun newFragment(): UserGuideFragment {
            val args = Bundle()
            val fragment = UserGuideFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var mUserGuideViewModel: UserGuideViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserGuideViewModel.setNavigator(this)
        mUserGuideViewModel.isLoading.set(false)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_user_guide

    override fun getViewModel(): UserGuideViewModel = mUserGuideViewModel

    override fun handleError(throwable: Throwable) {
        Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
    }
}