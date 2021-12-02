package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.states.HomeState

interface HomeFragmentView : MvpView {
    fun preLoadIntent() : Observable<Int>
    fun render(state: HomeState)
}