package kz.xbase.a_pay.network


import io.reactivex.Observable
import kz.xbase.mstroy.model.network.ResponseCheckuser
import kz.xbase.mstroy.model.network.ResponseVerifyPhone
import kz.xbase.mstroy.model.network.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface API {

    @POST("api/auth/mbusiness/check/user")
    fun checkUser(@Header("phone") phone:String) : Observable<ResponseCheckuser>

    @POST("api/auth/mbusiness/verify/phone")
    fun verifyPhone(@Header("phone") phone: String, @Header("code") code:String) : Observable<ResponseVerifyPhone>

    @Multipart
    @POST("api/auth/mbusiness/sign-up")
    fun signUp(
        @Part mainPhoto : MultipartBody.Part?,
        @Part firstPhoto : MultipartBody.Part?,
        @Part secondPhoto : MultipartBody.Part?,
        @Part thirdPhoto : MultipartBody.Part?,
        @Header("phone") phone : String,
        @Header("password") password : String,
        @Part("signUpString") signUpString: String
    ) : Observable<SignUpResponse>



//    @POST("api/auth/mobile/check-user")
//    fun checkUser(
//        @Body phone: CheckUserBody
//    ): Observable<CheckUserResponse>
//    @POST("api/auth/mobile/sign-in")
//    fun signIn(
//        @Body signInBody: SignInBody
//    ): Observable<SignInResponse>
//
//    @POST("api/auth/mobile/sign-up-sms")
//    fun signUpSms(
//        @Body verificationBody: VerificationBody
//    ): Observable<VerificationResponse>
//
//    @POST("api/auth/mobile/sign-up")
//    fun registerUser(
//        @Body registerBody: RegisterBody
//    ): Observable<RegisterResponse>
//
//    @GET("api/mobile/slider/all")
//    fun getSliders(
//    ): Observable<SlidersResponse>
//
//    @POST("api/auth/mobile/refresh")
//    fun refreshToken(
//        @Body refreshTokenBody: RefreshTokenBody
//    ): Observable<RefreshTokenResponse>
//
//    @POST("api/auth/mobile/reset-send-sms")
//    fun resetSendSms(
//            @Body verificationBody: CheckUserBody
//    ) : Observable<VerificationResponse>
//
//    @POST("api/auth/mobile/reset-sms")
//    fun checkSmsCodeForgot(
//            @Body verificationBody: VerificationBody
//    ) : Observable<VerificationResponse>
//
//    @Multipart
//    @POST("api/mobile/verification/biometry")
//    fun sendPhoto(
//            @Part userPhoto: MultipartBody.Part?,
//            @Part("phone") phone: RequestBody?,
//            @Header("inn") inn:String
//    ) : Observable<ResponseBody>
//
//    @POST("api/auth/mobile/set-password")
//    fun setForgottenPass(
//        @Body setForgottenPassBody: SetForgottenPassBody
//    ) : Observable<ResponseBody>
//
//    @GET("api/mobile/profile/balance")
//    fun getBalance(
//    ) : Observable<BalanceResponse>
//
//    @POST("api/mobile/profile/change-password")
//    fun setNewPassword(
//        @Body newPassBody: NewPassBody
//    ) : Observable<ResponseBody>
//
//    @Multipart
//    @POST("api/mobile/profile/ava")
//    fun setNewAva(
//        @Part("inn") inn : RequestBody?,
//        @Part ava : MultipartBody.Part?
//    ) : Observable<UpdateAvaResponse>
//
//    @POST("api/mobile/profile/push")
//    fun setPush(
//        @Header("push") push:Boolean
//    ) : Observable<ResponseBody>
//
//    @Multipart
//    @POST("api/mobile/profile/passport")
//    fun loadPassport(
//        @Part passportBack : MultipartBody.Part,
//        @Part passportFront : MultipartBody.Part
//    ) : Observable<ResponseBody>
//
//    @GET("api/mobile/transfer/history")
//    fun getTransfer(
//        @Query("pageNumber") pageNumber : String,
//        @Query("pageSize") pageSize : String
//    ) : Observable<GetTransferResponse>
//
//    @GET("api/mobile/transfer/find-user")
//    fun findUser(
//        @Query("phone") phone : String?,
//        @Query("inn") inn: String?
//    ) : Observable<FindUserResponse>
//
//    @POST("api/mobile/transfer/send")
//    fun sendTransfer(
//        @Body sendTransferBody: SendTransferBody
//    ) : Observable<SendTransferResponse>
//
//    @POST("api/mobile/transfer/approve")
//    fun approveTransfer(
//        @Body approveTransferBody: ApproveTransferBody
//    ) : Observable<SendTransferResponse>
//
//    @POST("api/mobile/profile/firebase")
//    fun setFirebaseToken(
//        @Body firebaseTokenBody: FirebaseTokenBody
//    ) : Observable<ResponseBody>
//
//    @POST("api/mobile/verification/qr")
//    fun verificationQR(
//        @Header("terminalName") terminalName : String
//    ) : Observable<ResponseBody>
//
//    @GET("api/mobile/notifications/a-pay")
//    fun getNotificationApay(
//        @Query("pageNumber") pageNumber: Int,
//        @Query("pageSize") pageSize: Int
//    ) : Observable<NotificationResponse>
//
//    @GET("api/mobile/notifications/loan")
//    fun getNotificationLoan(
//        @Query("pageNumber") pageNumber: Int,
//        @Query("pageSize") pageSize: Int
//    ) : Observable<NotificationResponse>
//
//    @GET("api/mobile/dictionaries/organisations?pageNumber=0&pageSize=100")
//    fun getOrganizations() : Observable<GetOrganizationsResponse>
//
//    @GET("api/mobile/loan/approved")
//    fun getApprovedLoans(
//        @Query("organisationId") organisationId:String
//    ) : Observable<GetApproveLoansResponse>
//
//    @GET("api/mobile/loan/active")
//    fun getActiveLoans(
//        @Query("organisationId") organisationId:String
//    ) : Observable<GetActiveLoansResponse>
//
//    @GET("api/mobile/loan/details")
//    fun getLoanDetail(
//            @Query("organisationId") organisationId:String,
//            @Query("contract") contract:String
//    ) : Observable<LoanDetailResponse>
//
//    @POST("api/mobile/loan/transactions/payment")
//    fun payCredit(
//            @Body payCreditModel: PayCreditModel
//    ) : Observable<CreditPaymentResponse>
//
//    @POST("api/mobile/loan/new")
//    fun newCredit(
//        @Body newCreditBody: NewCreditBody
//    ) : Observable<ResponseBody>
//
//    @POST("api/mobile/loan/transactions/delivery")
//    fun withdrawalCredit(
//        @Body creditWithdrawalBody: CreditWithdrawalBody
//    ) : Observable<CreditWithdrawalSmsResponse>
//
//    @POST("api/mobile/loan/transactions/delivery-approved")
//    fun withdrawalCreditSms(
//        @Body creditWithdrawalSmsBody: CreditWithdrawalSmsBody
//    ) : Observable<ResponseBody>
//
//    @GET("api/mobile/dictionaries/terminals")
//    fun getTerminals() : Observable<GetTerminalsResponse>

}