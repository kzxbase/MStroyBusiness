package kz.xbase.mstroy.states

sealed class CardAddState {
    object SuccessState : CardAddState()
    object Loading : CardAddState()
    data class ShowErrorMessage(val error:String) : CardAddState()
}