package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.CardsState
import kz.xbase.mstroy.views.CardsView

class CardsPresenter(ctx:Context) : MviBasePresenter<CardsView,CardsState>() {
    val apiInteractor = ApiInteractor(ctx)
    override fun bindIntents() {
        val chooseIntent : Observable<CardsState> =intent(CardsView::chooseIntent).flatMap {
            apiInteractor.chooseCard().startWith(CardsState.Loading).subscribeOn(Schedulers.io()).onErrorReturn {
                CardsState.ShowErrorMessage("")
            }
        }
        val loadIntent : Observable<CardsState> = intent(CardsView::loadIntent).flatMap {
            apiInteractor.getCards().startWith(CardsState.Loading).subscribeOn(Schedulers.io()).onErrorReturn {
                CardsState.ShowErrorMessage("")
            }
        }
        val allIntent = Observable.merge(chooseIntent,loadIntent).observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(allIntent,CardsView::render)
    }
}