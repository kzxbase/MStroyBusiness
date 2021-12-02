package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.HomeEditState
import kz.xbase.mstroy.views.HomeEditView

class HomeEditPresenter(ctx:Context) : MviBasePresenter<HomeEditView,HomeEditState>() {
    val apiInteractor = ApiInteractor(ctx)

    override fun bindIntents() {
        val editIntent : Observable<HomeEditState> = intent(HomeEditView::editIntent).flatMap {
            apiInteractor.editData().startWith(HomeEditState.Loading)
        }
        subscribeViewState(editIntent,HomeEditView::render)
    }
}