package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.model.mvi.AuthModel
import kz.xbase.mstroy.states.LoginPhonePassState
import kz.xbase.mstroy.states.LoginPhoneState

interface LoginPhonePassView: MvpView {

    fun checkPhonePassIntent() : Observable<AuthModel>

    fun goMainIntent() : Observable<Int>

    fun render(state: LoginPhonePassState)

}