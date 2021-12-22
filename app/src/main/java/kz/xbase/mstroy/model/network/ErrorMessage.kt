package kz.xbase.a_pay.model.network

data class ErrorMessage (
    val errorMessage: String,
    val isNoInternet: Boolean=false,
    val appIsDeprecated: Boolean = false
)