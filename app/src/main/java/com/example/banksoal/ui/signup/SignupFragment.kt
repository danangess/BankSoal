package com.example.banksoal.ui.signup

import android.os.Bundle
import android.widget.Toast
import com.example.banksoal.BR
import com.example.banksoal.R
import com.example.banksoal.databinding.FragmentSignupBinding
import com.example.banksoal.ui.base.BaseFragment
import com.example.banksoal.ui.main.MainActivity
import javax.inject.Inject

class SignupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>(), SignupNavigator {

    @Inject
    lateinit var mSignupViewModel: SignupViewModel

    companion object {
        const val TAG: String = "Login/SignUp"

        fun newFragment(): SignupFragment {
            val args = Bundle()
            val fragment = SignupFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSignupViewModel.setNavigator(this)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_signup

    override fun getViewModel(): SignupViewModel = mSignupViewModel

    override fun signUp() {
        val firstName = viewDataBinding.etFirstName.text.toString()
        val lastName = viewDataBinding.etLastName.text.toString()
        val username = viewDataBinding.etUsername.text.toString()
        val password = viewDataBinding.etPassword.text.toString()

        mSignupViewModel.registerUser(username, password, firstName, lastName)
    }

    override fun openMainActivity() {
        val intent = MainActivity.newIntent(this.requireContext())
        startActivity(intent)
        activity!!.finish()
    }

    override fun goBack() {
        activity?.onFragmentDetached(TAG)
    }

    override fun handleError(throwable: Throwable) {
        Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
    }
}