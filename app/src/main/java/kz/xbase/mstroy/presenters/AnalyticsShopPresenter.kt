package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import kz.xbase.mstroy.fragments.analytics.AnalyticsShopFragment
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.AnalyticsShopState
import kz.xbase.mstroy.views.AnalyticsShopView

class AnalyticsShopPresenter(ctx:Context) : MviBasePresenter<AnalyticsShopView,AnalyticsShopState>() {
    val apiInteractor = ApiInteractor(ctx)
    override fun bindIntents() {
        val preLoadIntent : Observable<AnalyticsShopState> = intent(AnalyticsShopView::preLoadIntent).flatMap {
            apiInteractor.getShopAnalytics().startWith(AnalyticsShopState.Loading).onErrorReturn {
                AnalyticsShopState.ShowErrorMessage("")
            }
        }
        subscribeViewState(preLoadIntent,AnalyticsShopView::render)
    }
}