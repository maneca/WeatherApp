package com.example.weatherapp

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class DisposableViewModel(private val disposables: CompositeDisposable = CompositeDisposable()): ViewModel() {

    /**
     * Adds a disposable to the subscriptions
     *
     * @param disposable the disposable to add, not null
     */
    fun addDisposable(@NonNull disposable: Disposable){
        disposables.add(disposable)
    }

    /**
     * Same as #addDisposable, just in ext flavour
     *
     */
    fun Disposable.addToDisposables(){
        disposables.add(this)
    }

    /**
     * Returns true if there is any disposable
     */
    protected fun hasDisposables() = disposables.size() > 0

    /**
     * Dispose the added disposables when the ViewModel is no longer used and will be destroyed.
     */
    public override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}