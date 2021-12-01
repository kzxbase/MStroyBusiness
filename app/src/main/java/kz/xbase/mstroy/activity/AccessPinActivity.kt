package kz.xbase.mstroy.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_access_pin.*
import kotlinx.android.synthetic.main.activity_create_pin.*
import kotlinx.android.synthetic.main.activity_create_pin.cv_biometry
import kotlinx.android.synthetic.main.activity_create_pin.cv_delete
import kotlinx.android.synthetic.main.activity_create_pin.cv_eight
import kotlinx.android.synthetic.main.activity_create_pin.cv_five
import kotlinx.android.synthetic.main.activity_create_pin.cv_four
import kotlinx.android.synthetic.main.activity_create_pin.cv_nine
import kotlinx.android.synthetic.main.activity_create_pin.cv_one
import kotlinx.android.synthetic.main.activity_create_pin.cv_seven
import kotlinx.android.synthetic.main.activity_create_pin.cv_six
import kotlinx.android.synthetic.main.activity_create_pin.cv_three
import kotlinx.android.synthetic.main.activity_create_pin.cv_two
import kotlinx.android.synthetic.main.activity_create_pin.cv_zero
import kotlinx.android.synthetic.main.activity_create_pin.pin1
import kotlinx.android.synthetic.main.activity_create_pin.pin2
import kotlinx.android.synthetic.main.activity_create_pin.pin3
import kotlinx.android.synthetic.main.activity_create_pin.pin4
import kz.xbase.mstroy.R
import kz.xbase.mstroy.utils.SessionManager
import java.util.concurrent.Executor

class AccessPinActivity : AppCompatActivity() {
    private var pin=""
    private var pin_images:ArrayList<FloatingActionButton> = ArrayList()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access_pin)
        pin_images.addAll(listOf(pin1,pin2,pin3,pin4))
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("M-Business")
            .setSubtitle("Авторизуйтесь с помощью биометрии")
            .setNegativeButtonText("Зайти с помощью PIN-кода")
            .build()
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)

                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@AccessPinActivity, HomeActivity::class.java))
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })
        setListeners()
        sessionManager = SessionManager(this)
    }
    private fun setListeners() {
        cv_one.setOnClickListener { addSymbol("1") }
        cv_two.setOnClickListener { addSymbol("2") }
        cv_three.setOnClickListener { addSymbol("3") }
        cv_four.setOnClickListener { addSymbol("4") }
        cv_five.setOnClickListener { addSymbol("5") }
        cv_six.setOnClickListener { addSymbol("6") }
        cv_seven.setOnClickListener { addSymbol("7") }
        cv_eight.setOnClickListener { addSymbol("8") }
        cv_nine.setOnClickListener { addSymbol("9") }
        cv_zero.setOnClickListener { addSymbol("0") }
        cv_delete.setOnClickListener { deleteSymbol() }
        cv_biometry.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
    private fun addSymbol(pressed:String){
        if(pin.length<4){
            pin_images[pin.length].backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            pin=pin+pressed
            Log.d("PINAS",pin)
            if(pin.length==4) {
                if (sessionManager.checkPin(pin)) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Snackbar.make(cv_delete, "PIN неправильный", Snackbar.LENGTH_SHORT).show()
                    deleteAll()
                }
            }
        }
    }
    private fun deleteSymbol(){
        if(pin.length>0){
            pin_images[pin.length-1].backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.grey))
            pin=pin.subSequence(0,pin.length-1).toString()
        }
    }
    private fun deleteAll(){
        Handler().postDelayed(Runnable {
            try {
                deleteSymbol()
                if(pin.length>0)
                    deleteAll()
            }catch (ex:Exception){}

        },200 )
    }
}