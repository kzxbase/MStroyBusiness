package kz.xbase.mstroy.states

sealed class NotificationListState {
    object MainState : NotificationListState()
    object Loading : NotificationListState()
    data class ShowErrorMessage(val error : String) : NotificationListState()
}