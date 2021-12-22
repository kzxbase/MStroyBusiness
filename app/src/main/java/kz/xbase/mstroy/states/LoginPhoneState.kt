package kz.xbase.mstroy.states

import kz.xbase.mstroy.model.network.ResponseCheckuser

sealed class LoginPhoneState {
    object MainState : LoginPhoneState()
    object Loading : LoginPhoneState()
    data class HasUserState(val isRegistered:ResponseCheckuser) : LoginPhoneState()
    data class ShowErrorMessage(val error:String) : LoginPhoneState()
}