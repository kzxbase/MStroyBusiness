package kz.xbase.mstroy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.replace
import kz.xbase.mstroy.fragments.home.CardAddFragment
import kz.xbase.mstroy.fragments.home.CardsFragment
import kz.xbase.mstroy.fragments.home.HomeEditFragment
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
    fun navigateHomeEditFragment() {
        val fragment = HomeEditFragment.newInstance()
        fragment.replace(supportFragmentManager,true)
    }
    fun navigateCardAddFragment() {
        val fragment = CardAddFragment.newInstance()
        fragment.replace(supportFragmentManager,true)
    }
    fun navigateCardsFragment() {
        val fragment = CardsFragment.newInstance()
        fragment.replace(supportFragmentManager,true)
    }
}