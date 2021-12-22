package kz.xbase.mstroy.network

import android.content.Context
import android.util.Base64
import io.reactivex.Observable
import kz.xbase.mstroy.App
import kz.xbase.mstroy.model.mvi.AuthModel
import kz.xbase.mstroy.model.mvi.NewPassModel
import kz.xbase.mstroy.states.*
import kz.xbase.mstroy.utils.SessionManager

class ApiInteractor(ctx: Context) {
    val api = App.api.api
    val sessionManager = SessionManager(ctx)

    fun toBase64(text:String):String{
        val data: ByteArray = text.encodeToByteArray()
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }

    //LoginPhoneFragment
    fun checkHasUser(number:String) : Observable<LoginPhoneState> {
       return api.checkUser(toBase64(number)).flatMap {
            Observable.just(LoginPhoneState.HasUserState(it))
       }
    }
    //PhoneSmsFragment
    fun checkSms(authModel: AuthModel) : Observable<PhoneSmsState> {
        return api.verifyPhone(toBase64(authModel.phone),toBase64(authModel.pass)).flatMap {
            Observable.just(PhoneSmsState.checkedSmsState(it.cityList))
        }
    }
    fun resendSms(number: String) : Observable<PhoneSmsState> {
        return api.checkUser(toBase64(number)).flatMap {
            Observable.just(PhoneSmsState.SmsSentState)
        }
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
    fun checkSmsForgot(authModel: AuthModel) : Observable<PhoneSmsState> {
        return Observable.just(PhoneSmsState.checkedSmsState(listOf()))
    }
    fun resendSmsForgot() : Observable<PhoneSmsState> {
        return Observable.just(PhoneSmsState.SmsSentState)
    }

    //HomeFragment
    fun loadData() : Observable<HomeState> {
        return Observable.just(HomeState.MainState(""))
    }

    //HomeEditFragment
    fun editData() : Observable<HomeEditState> {
        return Observable.just(HomeEditState.SuccessState)
    }

    //CardAddFragment
    fun addCard() : Observable<CardAddState> {
        return Observable.just(CardAddState.SuccessState)
    }

    //CardsFragment
    fun getCards() : Observable<CardsState> {
        return Observable.just(CardsState.MainState)
    }
    fun chooseCard() : Observable<CardsState> {
        return Observable.just(CardsState.ChoosedState)
    }

    //NotificationListFragment
    fun getNotifications() : Observable<NotificationListState> {
        return Observable.just(NotificationListState.MainState)
    }
    //AnalyticsPersonalFragment
    fun getPersonal() : Observable<AnalyticsPersonalState> {
        return Observable.just(AnalyticsPersonalState.MainState(""))
    }
    //AnalyticsShopFragment
    fun getShopAnalytics() : Observable<AnalyticsShopState> {
        return Observable.just(AnalyticsShopState.MainState(""))
    }

}