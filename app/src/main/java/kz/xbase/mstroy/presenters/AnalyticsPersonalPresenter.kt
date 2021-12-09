package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.AnalyticsPersonalState
import kz.xbase.mstroy.views.AnalyticsPersonalView

class AnalyticsPersonalPresenter(ctx : Context) : MviBasePresenter<AnalyticsPersonalView,AnalyticsPersonalState>() {
    val apiInteractor = ApiInteractor(ctx)
    override fun bindIntents() {
        val preloadIntent : Observable<AnalyticsPersonalState> = intent(AnalyticsPersonalView::preLoadIntent).flatMap {
            apiInteractor.getPersonal().startWith(AnalyticsPersonalState.Loading).onErrorReturn {
                AnalyticsPersonalState.ShowErrorMessage("")
            }
        }
        subscribeViewState(preloadIntent,AnalyticsPersonalView::render)
    }
}