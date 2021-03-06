package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.PhoneSmsState
import kz.xbase.mstroy.views.PhoneSmsView
import java.util.concurrent.TimeUnit

class LoginForgotPresenter(ctx:Context) : MviBasePresenter<PhoneSmsView,PhoneSmsState>() {
    val apiInteractor = ApiInteractor(ctx)

    override fun bindIntents() {
        val checkSmsIntent : Observable<PhoneSmsState> = intent(PhoneSmsView::checkSmsIntent).flatMap {
            apiInteractor.checkSmsForgot(it).startWith(PhoneSmsState.Loading).subscribeOn(Schedulers.io())
        }
        val resendIntent : Observable<PhoneSmsState> = intent(PhoneSmsView::resendIntent).flatMap {
            apiInteractor.resendSmsForgot().startWith(PhoneSmsState.Loading).subscribeOn(Schedulers.io())
        }
        val startTimerIntent : Observable<PhoneSmsState> = intent(PhoneSmsView::startTimerIntent).flatMap {
            Observable.intervalRange(0,60,0,1, TimeUnit.SECONDS).flatMap {
                if(it.toInt()==59){
                    Observable.just(PhoneSmsState.ResendAvailableState)
                }else {
                    Observable.just(PhoneSmsState.TimerState(59 - it.toInt()))
                }
            }.subscribeOn(Schedulers.io())
        }
        val allIntent = Observable.merge(checkSmsIntent,resendIntent,startTimerIntent).observeOn(
            AndroidSchedulers.mainThread())
        subscribeViewState(allIntent,PhoneSmsView::render)
    }
}