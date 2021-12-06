package kz.xbase.mstroy.states

sealed class CardsState {
    object MainState : CardsState()
    object Loading : CardsState()
    data class ShowErrorMessage(val error : String) : CardsState()
    object ChoosedState : CardsState()
}