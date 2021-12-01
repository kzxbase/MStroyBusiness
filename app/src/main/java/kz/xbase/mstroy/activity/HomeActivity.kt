package kz.xbase.mstroy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.replace
import kz.xbase.mstroy.fragments.home.HomeFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigateHomeFragment()
    }
    fun navigateHomeFragment(){
        val fragment = HomeFragment.newInstance()
        fragment.replace(supportFragmentManager,true)
    }
}