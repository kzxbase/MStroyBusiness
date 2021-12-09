package kz.xbase.mstroy.states

sealed class AnalyticsPersonalState {
    data class MainState(val data:String) : AnalyticsPersonalState()
    object Loading : AnalyticsPersonalState()
    data class ShowErrorMessage(val error : String) : AnalyticsPersonalState()
}