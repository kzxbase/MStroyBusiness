package kz.xbase.mstroy.presenters

import android.content.Context
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import kz.xbase.mstroy.network.ApiInteractor
import kz.xbase.mstroy.states.PhoneSmsState
import kz.xbase.mstroy.views.PhoneSmsView

class PhoneSmsPresenter(ctx:Context) : MviBasePresenter<PhoneSmsView,PhoneSmsState>() {
    val apiInteractor = ApiInteractor(ctx)
    override fun bindIntents() {

    }
}