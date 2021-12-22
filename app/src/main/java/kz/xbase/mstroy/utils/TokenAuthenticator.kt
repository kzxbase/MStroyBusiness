package kz.xbase.mstroy.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.xbase.mstroy.App
import kz.xbase.mstroy.activity.LoginActivity
import okhttp3.*
import retrofit2.HttpException

//class TokenAuthenticator(val ctx: Context) : Authenticator {
//
//    private val gson = Gson()
//
//    override fun authenticate(route: Route?, response: Response): Request? {
//        val token: String = SessionManager(ctx).token
//
//        synchronized(this) {
//            val refreshToken: String = SessionManager(ctx).refreshToken
//            val newToken: String = SessionManager(ctx).token
//            if (newToken != token) {
//                return newRequestWithAccessToken(response.request, newToken!!)
//            }
////
////            val refreshedToken = App.api.api.refreshToken(RefreshTokenBody(SessionManager(ctx).refreshToken)).onErrorReturn {
////                if (it is HttpException) {
////                    if (it.code() == 409) {
////                        SessionManager(ctx).isLogged = false
////                        val intent = Intent(ctx, LoginActivity::class.java)
////                        intent.flags =
////                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                        ctx.startActivity(intent)
////                    }
////                }
////                throw it
////            }
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .blockingFirst()
//            SessionManager(ctx).token = refreshedToken.accessToken
//            SessionManager(ctx).setRefreshtoken(refreshedToken.refreshToken)
//
//            return newRequestWithAccessToken(response.request, refreshedToken.accessToken)
//        }
//    }
//
//
//    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
//        return request.newBuilder()
//            .header("Authorization", "Bearer $accessToken")
//            .build()
//    }
//}