package kz.xbase.mstroy.states

import kz.xbase.mstroy.model.network.City

sealed class PhoneSmsState {
    object MainState : PhoneSmsState()
    object Loading : PhoneSmsState()
    object ResendAvailableState : PhoneSmsState()
    object SmsSentState : PhoneSmsState()
    data class TimerState(val secLeft:Int) : PhoneSmsState()
    data class ShowErrorMessage(val message:String) : PhoneSmsState()
    data class checkedSmsState(val cityList: List<City>) : PhoneSmsState()
}