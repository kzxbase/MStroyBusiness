package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.RegisterBusinessState
import kz.xbase.mstroy.views.RegisterBusinessView

class RegisterBusinessPresenter(ctx:Context) : MviBasePresenter<RegisterBusinessView,RegisterBusinessState>() {
    val apiInteractor = ApiInteractor(ctx)
    override fun bindIntents() {
        val uploadDataIntent :Observable<RegisterBusinessState> = intent(RegisterBusinessView::uploadDataIntent).flatMap {
            apiInteractor.uploadData(it).startWith(RegisterBusinessState.Loading).subscribeOn(Schedulers.io())
        }
        uploadDataIntent.observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(uploadDataIntent,RegisterBusinessView::render)
    }
}