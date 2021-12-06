package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.states.CardsState

interface CardsView : MvpView {
    fun chooseIntent() : Observable<String>
    fun loadIntent() : Observable<Int>
    fun render(state : CardsState)
}