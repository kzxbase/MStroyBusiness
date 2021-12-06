package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.states.CardAddState

interface CardAddView : MvpView{
    fun addIntent() : Observable<String>
    fun render(state : CardAddState)
}