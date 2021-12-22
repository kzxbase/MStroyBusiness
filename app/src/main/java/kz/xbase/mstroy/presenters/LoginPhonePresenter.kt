package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.xbase.a_pay.views.LoginPhoneView
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.LoginPhoneState
import kz.xbase.mstroy.utils.HttpErrorHandler
import java.util.concurrent.TimeUnit

class LoginPhonePresenter(val ctx: Context) : MviBasePresenter<LoginPhoneView,LoginPhoneState>() {
    val apiInteractor = ApiInteractor(ctx)
    val httpErrorHandler = HttpErrorHandler()
    override fun bindIntents() {
        val mainIntent : Observable<LoginPhoneState> = intent(LoginPhoneView::goMainIntent).flatMap {
            Observable.just(LoginPhoneState.MainState)
        }
        val checkHasUserInent : Observable<LoginPhoneState> = intent(LoginPhoneView::checkHasUserIntent).flatMap {
            apiInteractor.checkHasUser(it).startWith(LoginPhoneState.Loading).onErrorReturn {
                LoginPhoneState.ShowErrorMessage(httpErrorHandler.getErrorMessage(it).errorMessage)
            }.subscribeOn(Schedulers.io())
        }
        val allIntent = Observable.merge(mainIntent,checkHasUserInent).observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(allIntent,LoginPhoneView::render)
    }
}