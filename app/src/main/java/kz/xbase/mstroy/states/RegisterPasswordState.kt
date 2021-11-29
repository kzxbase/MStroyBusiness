package kz.xbase.mstroy.states

sealed class RegisterPasswordState {
    object SuccesState:RegisterPasswordState()
    object Loading:RegisterPasswordState()
    data class ShowErrorMessage(val message:String) : RegisterPasswordState()
}