package kz.xbase.mstroy.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kz.xbase.mstroy.R
import kz.xbase.mstroy.utils.SessionManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val sessionManager = SessionManager(this)
        Handler().postDelayed(Runnable {
            if(!sessionManager.isLogged || !sessionManager.getisPinOn()){
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this,AccessPinActivity::class.java))
                finish()
            }
        },1500)
    }
}