package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.states.HomeEditState

interface HomeEditView : MvpView {
    fun editIntent() : Observable<String>
    fun render(state : HomeEditState)
}