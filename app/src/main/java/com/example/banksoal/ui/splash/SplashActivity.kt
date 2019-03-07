package com.example.banksoal.ui.splash

import android.animation.Animator
import android.animation.ValueAnimator
import com.example.banksoal.ui.base.BaseActivity
import android.os.Bundle
import android.view.animation.BounceInterpolator
import com.example.banksoal.BR
import com.example.banksoal.ui.login.LoginActivity
import com.example.banksoal.R
import com.example.banksoal.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity: BaseActivity<com.example.banksoal.databinding.ActivitySplashBinding, SplashViewModel>(), SplashNavigator {
    @Inject
    lateinit var mSplashViewModel: SplashViewModel

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModel(): SplashViewModel {
        return mSplashViewModel
    }

    override fun openLoginActivity() {
        val intent = LoginActivity().newIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun openMainActivity() {
        val intent = MainActivity().newIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSplashViewModel.setNavigator(this)
        startApp()
    }

    private fun startApp() {
        // Intro animation configuration.
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.addUpdateListener {
        }
        valueAnimator.interpolator = BounceInterpolator()
        valueAnimator.duration = 1000

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                mSplashViewModel.decideNextActivity()
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {
                mSplashViewModel.startSeeding()
            }

        })

        valueAnimator.start()
    }
}