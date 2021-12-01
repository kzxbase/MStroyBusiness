package kz.xbase.mstroy.states

sealed class LoginPhonePassState {
    object MainState : LoginPhonePassState()
    object Loading : LoginPhonePassState()
    object SuccessState : LoginPhonePassState()
    data class ShowErrorMessage(val error:String) : LoginPhonePassState()
}