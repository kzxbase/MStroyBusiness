package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.states.RegisterBusinessState


interface RegisterBusinessView : MvpView {

    fun uploadDataIntent() : Observable<String>

    fun render(state : RegisterBusinessState)
}