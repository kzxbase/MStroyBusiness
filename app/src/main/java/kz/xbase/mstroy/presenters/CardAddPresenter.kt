package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.CardAddState
import kz.xbase.mstroy.views.CardAddView

class CardAddPresenter(ctx:Context) : MviBasePresenter<CardAddView,CardAddState>(){
    val apiInteractor = ApiInteractor(ctx)
    override fun bindIntents() {
        val addIntent : Observable<CardAddState> = intent(CardAddView::addIntent).flatMap {
            apiInteractor.addCard().startWith(CardAddState.Loading)
        }
        subscribeViewState(addIntent,CardAddView::render)
    }
}