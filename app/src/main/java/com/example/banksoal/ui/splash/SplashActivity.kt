package com.example.banksoal.ui.splash

import android.animation.Animator
import android.animation.ValueAnimator
import com.example.banksoal.ui.base.BaseActivity
import android.os.Bundle
import android.view.animation.BounceInterpolator
import android.widget.Toast
import com.example.banksoal.BR
import com.example.banksoal.ui.login.LoginActivity
import com.example.banksoal.R
import com.example.banksoal.databinding.ActivitySplashBinding
import com.example.banksoal.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    @Inject
    lateinit var mSplashViewModel: SplashViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun getViewModel(): SplashViewModel = mSplashViewModel

    override fun openLoginActivity() {
        val intent = LoginActivity.newIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun openMainActivity() {
        val intent = MainActivity.newIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSplashViewModel.isLoading.set(false)
        mSplashViewModel.setNavigator(this)
        startApp()
        mSplashViewModel.startSeeding()
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

            }

        })

        valueAnimator.start()
    }
}