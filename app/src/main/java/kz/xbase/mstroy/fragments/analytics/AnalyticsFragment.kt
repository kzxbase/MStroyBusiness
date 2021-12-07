package kz.xbase.mstroy.fragments.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_analytics.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.fragments.home.NotificationFragment

class AnalyticsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analytics,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }
    private fun setListeners(){
        cv_tab_personal.setOnClickListener {
            cv_tab_personal.setCardBackgroundColor(resources.getColor(R.color.white))
            tv_tab_personal.setTextColor(resources.getColor(R.color.black))
            cv_tab_shop.setCardBackgroundColor(resources.getColor(R.color.black))
            tv_tab_shop.setTextColor(resources.getColor(R.color.white))
            cv_tab_good.setCardBackgroundColor(resources.getColor(R.color.black))
            tv_tab_good.setTextColor(resources.getColor(R.color.white))
        }
        cv_tab_good.setOnClickListener {
            cv_tab_personal.setCardBackgroundColor(resources.getColor(R.color.black))
            tv_tab_personal.setTextColor(resources.getColor(R.color.white))
            cv_tab_shop.setCardBackgroundColor(resources.getColor(R.color.black))
            tv_tab_shop.setTextColor(resources.getColor(R.color.white))
            cv_tab_good.setCardBackgroundColor(resources.getColor(R.color.white))
            tv_tab_good.setTextColor(resources.getColor(R.color.black))
        }
        cv_tab_shop.setOnClickListener {
            cv_tab_personal.setCardBackgroundColor(resources.getColor(R.color.black))
            tv_tab_personal.setTextColor(resources.getColor(R.color.white))
            cv_tab_shop.setCardBackgroundColor(resources.getColor(R.color.white))
            tv_tab_shop.setTextColor(resources.getColor(R.color.black))
            cv_tab_good.setCardBackgroundColor(resources.getColor(R.color.black))
            tv_tab_good.setTextColor(resources.getColor(R.color.white))
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = AnalyticsFragment()
    }
}