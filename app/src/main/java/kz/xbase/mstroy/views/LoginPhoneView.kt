package kz.xbase.a_pay.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.states.LoginPhoneState


interface LoginPhoneView : MvpView {

    fun checkHasUserIntent() : Observable<String>

    fun goMainIntent() : Observable<Int>

    fun render(state: LoginPhoneState)

}