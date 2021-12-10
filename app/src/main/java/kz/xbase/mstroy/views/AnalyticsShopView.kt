package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.states.AnalyticsShopState

interface AnalyticsShopView : MvpView {
    fun preLoadIntent() : Observable<Int>
    fun render(state : AnalyticsShopState)
}