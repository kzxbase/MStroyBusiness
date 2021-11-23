package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.states.PhoneSmsState

interface PhoneSmsView : MvpView{

    fun checkSmsIntent() : Observable<String>

    fun resendIntent() : Observable<String>

    fun startTimerIntent() : Observable<Int>

    fun render(state : PhoneSmsState)
}