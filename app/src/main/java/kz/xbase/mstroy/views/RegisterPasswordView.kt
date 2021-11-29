package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.model.mvi.NewPassModel
import kz.xbase.mstroy.states.RegisterPasswordState

interface RegisterPasswordView : MvpView {

    fun setPasswordIntent() : Observable<NewPassModel>

    fun render(state:RegisterPasswordState)
}