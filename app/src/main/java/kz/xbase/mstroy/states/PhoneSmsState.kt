package kz.xbase.mstroy.states

sealed class PhoneSmsState {
    object MainState : PhoneSmsState()
    object Loading : PhoneSmsState()
    object ResendAvailableState : PhoneSmsState()
    object SmsSentState : PhoneSmsState()
    data class TimerState(val secLeft:Int) : PhoneSmsState()
    data class ShowErrorMessage(val message:String) : PhoneSmsState()
    data class checkedSmsState(val isCorrect:Boolean) : PhoneSmsState()
}