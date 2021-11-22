package kz.xbase.mstroy.states

sealed class LoginPhoneState {
    object MainState : LoginPhoneState()
    object Loading : LoginPhoneState()
    data class HasUserState(val isRegistered:Boolean) : LoginPhoneState()
    data class ShowErrorMessage(val error:String) : LoginPhoneState()
}