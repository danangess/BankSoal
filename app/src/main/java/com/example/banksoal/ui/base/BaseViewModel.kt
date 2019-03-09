package com.example.banksoal.ui.base

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.example.banksoal.data.DataManager
import com.example.banksoal.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> (
    val dataManager: DataManager,
    val schedulerProvider: SchedulerProvider
) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var mNavigator: WeakReference<N>

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun getNavigator(): N = mNavigator.get()!!

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }
}