package kz.xbase.mstroy.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.replace
import kz.xbase.mstroy.bottomSheets.RegisterMapBottomSheet
import kz.xbase.mstroy.fragments.login.*
import kz.xbase.mstroy.model.mvi.RegisterModel
import kz.xbase.mstroy.model.network.City

class LoginActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        navigateLoginPhoneFragment()
        MapKitFactory.setApiKey("abdd6504-88e2-4856-a4fd-d9739ba0320e")
        MapKitFactory.initialize(this)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.container)
        if(requestCode==1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(fragment is RegisterMapBottomSheet){
                fragment.onLocationPermissionGranted()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.container)
        if(requestCode==2 && resultCode == Activity.RESULT_OK){
            if(fragment is RegisterMapBottomSheet){
                fragment.gpsEnabled()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }



}