package kz.xbase.mstroy.states

sealed class HomeState {
    data class MainState(val data : String) : HomeState()
    object Loading : HomeState()
    data class ShowErrorMessage(val error:String) : HomeState()
}