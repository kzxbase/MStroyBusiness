package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.NotificationListState
import kz.xbase.mstroy.views.NotificationListView

class NotificationListPresenter(ctx : Context) : MviBasePresenter<NotificationListView,NotificationListState>() {
    val apiInteractor = ApiInteractor(ctx)
    override fun bindIntents() {
        val loadIntent : Observable<NotificationListState> = intent(NotificationListView::getNotiIntent).flatMap {
            apiInteractor.getNotifications().startWith(NotificationListState.Loading).onErrorReturn {
                NotificationListState.ShowErrorMessage("")
            }
        }
        subscribeViewState(loadIntent,NotificationListView::render)
    }
}