package kz.xbase.mstroy.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import kz.xbase.mstroy.states.NotificationListState

interface NotificationListView : MvpView {
    fun getNotiIntent() : Observable<Int>
    fun render(state : NotificationListState)
}