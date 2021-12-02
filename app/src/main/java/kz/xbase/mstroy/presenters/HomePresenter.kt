package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.HomeState
import kz.xbase.mstroy.views.HomeFragmentView
import java.util.concurrent.TimeUnit

class HomePresenter(ctx:Context) : MviBasePresenter<HomeFragmentView,HomeState>() {
    val apiInteractor = ApiInteractor(ctx)

    override fun bindIntents() {
        val preLoadIntent : Observable<HomeState> = intent(HomeFragmentView::preLoadIntent).flatMap {
            apiInteractor.loadData().startWith(HomeState.Loading)
        }
        subscribeViewState(preLoadIntent,HomeFragmentView::render)
    }
}