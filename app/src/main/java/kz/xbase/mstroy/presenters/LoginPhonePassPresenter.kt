package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.xbase.a_pay.views.LoginPhoneView
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.LoginPhonePassState
import kz.xbase.mstroy.states.LoginPhoneState
import kz.xbase.mstroy.views.LoginPhonePassView

class LoginPhonePassPresenter(val ctx:Context) : MviBasePresenter<LoginPhonePassView,LoginPhonePassState>() {
        val apiInteractor = ApiInteractor(ctx)
    override fun bindIntents() {
        val checkPhonePassIntent : Observable<LoginPhonePassState> = intent(LoginPhonePassView::checkPhonePassIntent).flatMap {
            apiInteractor.checkPhonePass(it).startWith(LoginPhonePassState.Loading).subscribeOn(Schedulers.io())
        }
        val mainIntent : Observable<LoginPhonePassState> = intent(LoginPhonePassView::goMainIntent).flatMap {
            Observable.just(LoginPhonePassState.MainState)
        }
        val allIntent = Observable.merge(mainIntent,checkPhonePassIntent).observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(allIntent, LoginPhonePassView::render)
    }
}