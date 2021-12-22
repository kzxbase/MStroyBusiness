package kz.xbase.mstroy.utils

import com.google.gson.Gson
import kz.xbase.a_pay.model.network.ErrorJson
import kz.xbase.a_pay.model.network.ErrorMessage
import retrofit2.HttpException
import java.lang.Exception

class HttpErrorHandler {

    fun getErrorMessage(error: Throwable): ErrorMessage {

        val gson = Gson()

        var appIsDeprecated = false
        val appIsDeprecatedId ="99999"
        return if (error is HttpException) {
            try {
                error.response()?.errorBody().let {
                    when {
                        error.code() == 500 || error.code() == 503 -> {
                            appIsDeprecated = false
                            ErrorMessage(errorMessage = "К сожалению, возникла ошибка при выполнении операции. Повторите попытку позже.", appIsDeprecated = appIsDeprecated)
                        }
                        else -> {
                            appIsDeprecated = false
                            val errorBody = it
                            val adapter =
                                gson.getAdapter<ErrorJson>(ErrorJson::class.java)
                            val errorObj = adapter.fromJson(errorBody?.string())
                            if (error.code() == 403 && errorObj.status == appIsDeprecatedId) appIsDeprecated =
                                true
                            ErrorMessage(
                                errorMessage = errorObj.message,
                                appIsDeprecated = appIsDeprecated
                            )

                        }
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
                appIsDeprecated = false
                ErrorMessage(
                    errorMessage = "Ошибка, попробуйте перезагрузить страницу",
                    appIsDeprecated = appIsDeprecated
                )
            }
        } else {
            error.printStackTrace()
            appIsDeprecated = false
            ErrorMessage(errorMessage = "Что-то пошло не так, возможно у вас пропало интернет соединение.",appIsDeprecated = appIsDeprecated,isNoInternet = true)
        }
    }
}