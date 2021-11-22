package kz.xbase.mstroy.states

sealed class PhoneSmsState {
    object MainState : PhoneSmsState()
    object Loading : PhoneSmsState()
    object ResendAvailableState : PhoneSmsState()
    object SmsSentState : PhoneSmsState()
    data class ShowErrorMessage(val message:String) : PhoneSmsState()
    object correctSmsState : PhoneSmsState()
}