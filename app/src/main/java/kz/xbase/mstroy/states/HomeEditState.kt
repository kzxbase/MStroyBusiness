package kz.xbase.mstroy.states

sealed class HomeEditState {
    object SuccessState : HomeEditState()
    object Loading : HomeEditState()
    data class ShowErrorMessage(val error:String) : HomeEditState()
}