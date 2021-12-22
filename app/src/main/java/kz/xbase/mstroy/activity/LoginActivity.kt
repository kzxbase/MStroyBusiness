package kz.xbase.mstroy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.replace
import kz.xbase.mstroy.fragments.login.*
import kz.xbase.mstroy.model.mvi.RegisterModel
import kz.xbase.mstroy.model.network.City

class LoginActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        navigateLoginPhoneFragment()
    }
    fun navigateLoginPhoneFragment(){
        val fragment = LoginPhoneFragment.newInstance()
        fragment.replace(supportFragmentManager,true)
    }
    fun navigatePhoneSmsFragment(phone:String){
        val fragment = PhoneSmsFragment.newInstance(phone)
        fragment.replace(supportFragmentManager,true)
    }
    fun navigateRegisterBusinessFragment(cityList: List<City>){
        val fragment = RegisterBusinessFragment.newInstance(cityList)
        fragment.replace(supportFragmentManager,true)
    }
    fun navigateRegisterPassFragment(registerModel: RegisterModel) {
        val fragment = RegisterPasswordFragment.newInstance(registerModel)
        fragment.replace(supportFragmentManager,true)
    }
    fun navigateLoginPhonePassFragment(phone:String) {
        val fragment = LoginPhonePassFragment.newInstance(phone)
        fragment.replace(supportFragmentManager,true)
    }
    fun navigateLoginForgotFragment(phone: String){
        val fragment = LoginForgotFragment.newInstance(phone)
        fragment.replace(supportFragmentManager,true)
    }

}