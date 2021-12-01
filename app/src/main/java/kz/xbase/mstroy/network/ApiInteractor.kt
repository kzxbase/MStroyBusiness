package kz.xbase.mstroy.network

import android.content.Context
import io.reactivex.Observable
import kz.xbase.mstroy.model.mvi.AuthModel
import kz.xbase.mstroy.model.mvi.NewPassModel
import kz.xbase.mstroy.states.*
import java.util.concurrent.TimeUnit

class ApiInteractor(ctx: Context) {

    //LoginPhoneFragment
    fun checkHasUser(number:String) : Observable<LoginPhoneState> {
       return Observable.just(LoginPhoneState.HasUserState(true))
    }

    //PhoneSmsFragment
    fun checkSms(sms:String) : Observable<PhoneSmsState> {
        return Observable.just(PhoneSmsState.checkedSmsState(true))
    }
    fun resendSms() : Observable<PhoneSmsState> {
        return Observable.just(PhoneSmsState.SmsSentState)
    }

    //RegisterBusinessFragment
    fun uploadData(data:String) : Observable<RegisterBusinessState> {
        return Observable.just(RegisterBusinessState.SuccessState)
    }

    //RegisterPasswordFragment
    fun setPassword(passModel: NewPassModel) : Observable<RegisterPasswordState> {
        return Observable.just(RegisterPasswordState.SuccesState)
    }

    //LoginPhonePassFragment
    fun checkPhonePass(authModel: AuthModel) : Observable<LoginPhonePassState> {
        return Observable.just(LoginPhonePassState.SuccessState)
    }

    //LoginForgotFragment
    fun checkSmsForgot(sms:String) : Observable<PhoneSmsState> {
        return Observable.just(PhoneSmsState.checkedSmsState(true))
    }
    fun resendSmsForgot() : Observable<PhoneSmsState> {
        return Observable.just(PhoneSmsState.SmsSentState)
    }
}