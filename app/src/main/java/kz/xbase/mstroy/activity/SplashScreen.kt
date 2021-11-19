package kz.xbase.mstroy.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kz.xbase.mstroy.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_splashscreen)
        Handler().postDelayed(Runnable {
//            if(!sessionManager.isLogged || !sessionManager.getisPinOn()){
//                startActivity(Intent(this,LoginActivity::class.java))
//                finish()
//            }else{
//                startActivity(Intent(this,AccessPinActivity::class.java))
//                finish()
//            }
        },1000)
    }
}