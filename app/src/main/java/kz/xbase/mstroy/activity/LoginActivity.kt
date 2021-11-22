package kz.xbase.mstroy.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.replace
import kz.xbase.mstroy.fragments.LoginPhoneFragment
import kz.xbase.mstroy.fragments.PhoneSmsFragment
import kz.xbase.mstroy.utils.SessionManager

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

}