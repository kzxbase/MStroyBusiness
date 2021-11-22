package kz.xbase.mstroy.network

import android.content.Context
import io.reactivex.Observable
import kz.xbase.mstroy.states.LoginPhoneState
import java.util.concurrent.TimeUnit

class ApiInteractor(ctx: Context) {

    //LoginPhoneFragment
    fun checkHasUser(number:String) : Observable<LoginPhoneState> {
       return Observable.just(LoginPhoneState.HasUserState(false))
    }
}