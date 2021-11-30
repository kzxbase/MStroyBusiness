package kz.xbase.mstroy.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_pin.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.utils.SessionManager
import java.util.concurrent.Executor

class RegisterPinActivity:AppCompatActivity() {
    private var pin=""
    private var pin_images:ArrayList<FloatingActionButton> = ArrayList()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var sessionManager:SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pin)
        pin_images.addAll(listOf(pin1,pin2,pin3,pin4))
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
        btn_create.setOnClickListener {
            sessionManager.setPin(pin)
            sessionManager.setIsPinOn(true)
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
        btn_skip.setOnClickListener {
            sessionManager.setIsPinOn(false)
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }
    private fun addSymbol(pressed:String){
        if(pin.length<4){
            pin_images[pin.length].backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            pin=pin+pressed
            Log.d("PINAS",pin)
        }
    }
    private fun deleteSymbol(){
        if(pin.length>0){
            pin_images[pin.length-1].backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.grey))
            pin=pin.subSequence(0,pin.length-1).toString()
        }
    }
}