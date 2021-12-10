package kz.xbase.mstroy.states

sealed class AnalyticsShopState {
    data class MainState(val data:String) : AnalyticsShopState()
    object Loading : AnalyticsShopState()
    data class ShowErrorMessage(val error:String) : AnalyticsShopState()
}