package kz.xbase.mstroy.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_notification.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.HomeActivity

class NotificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }
    private fun setListeners(){
        cv_back.setOnClickListener {
            (activity as HomeActivity).navigateHomeFragment()
        }
        cv_noti_order.setOnClickListener {
            (activity as HomeActivity).navigateNotificationListFragment(true)
        }
        cv_noti_discount.setOnClickListener {
            (activity as HomeActivity).navigateNotificationListFragment(false)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotificationFragment()
    }

}