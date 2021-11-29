package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.RegisterPasswordState
import kz.xbase.mstroy.views.RegisterPasswordView

class RegisterPasswordPresenter(ctx:Context) : MviBasePresenter<RegisterPasswordView,RegisterPasswordState>() {
    val apiInteractor = ApiInteractor(ctx)
    override fun bindIntents() {
        val setPasswordIntent:Observable<RegisterPasswordState> = intent(RegisterPasswordView::setPasswordIntent).flatMap {
            apiInteractor.setPassword(it).startWith(RegisterPasswordState.Loading)
        }
        subscribeViewState(setPasswordIntent,RegisterPasswordView::render)
    }
}