package kz.xbase.mstroy.states

sealed class RegisterBusinessState {
    object MainState : RegisterBusinessState()
    object Loading : RegisterBusinessState()
    object SuccessState : RegisterBusinessState()
    data class ShowErrorMessage(val message : String) : RegisterBusinessState()
}