package kz.xbase.mstroy.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.replace
import kz.xbase.mstroy.fragments.analytics.AnalyticsFragment
import kz.xbase.mstroy.fragments.analytics.PersonalItemFragment
import kz.xbase.mstroy.fragments.home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigateHomeFragment()
        setListeners()
    }

    private fun setListeners(){
        nav_view.setOnItemSelectedListener {
            if(it.itemId==R.id.analytics){
                navigateAnalyticsFragment()
            }
            true
        }
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
    fun navigateNotificationFragment() {
        val fragment = NotificationFragment.newInstance()
        fragment.replace(supportFragmentManager,true)
    }
    fun navigateNotificationListFragment(isOrder:Boolean) {
        val fragment = NotificationListFragment.newInstance(isOrder)
        fragment.replace(supportFragmentManager,true)
    }
    fun navigatePromoteFragment() {
        val fragment = PromoteFragment.newInstance()
        fragment.replace(supportFragmentManager,true)
    }
    fun navigateAnalyticsFragment(){
        val fragment = AnalyticsFragment.newInstance()
        fragment.replace(supportFragmentManager,true)
    }
    fun navigateAnalyticsPersonalItemFragment(){
        val fragment = PersonalItemFragment.newInstance()
        fragment.replace(supportFragmentManager,true)
    }
}