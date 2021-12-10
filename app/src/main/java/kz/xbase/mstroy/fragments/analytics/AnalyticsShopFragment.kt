package kz.xbase.mstroy.fragments.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_analytics_shop.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.presenters.AnalyticsShopPresenter
import kz.xbase.mstroy.states.AnalyticsShopState
import kz.xbase.mstroy.views.AnalyticsShopView

class AnalyticsShopFragment : MviFragment<AnalyticsShopView,AnalyticsShopPresenter>(),AnalyticsShopView {
    private lateinit var loadTrigger : PublishSubject<Int>
    var isFirstRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadTrigger = PublishSubject.create()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analytics_shop,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }
    private fun setListeners(){
        tv_week.setOnClickListener {
            tv_week.setBackgroundColor(resources.getColor(R.color.white))
            tv_week.setTextColor(resources.getColor(R.color.black))
            tv_month.setBackgroundColor(resources.getColor(R.color.grey_back))
            tv_month.setTextColor(resources.getColor(R.color.grey))
            ll_months.visibility = View.GONE
            ll_months_data.visibility = View.GONE
            ll_week.visibility = View.VISIBLE
            ll_weeks_data.visibility = View.VISIBLE
        }
        tv_month.setOnClickListener {
            tv_week.setBackgroundColor(resources.getColor(R.color.grey_back))
            tv_week.setTextColor(resources.getColor(R.color.grey))
            tv_month.setBackgroundColor(resources.getColor(R.color.white))
            tv_month.setTextColor(resources.getColor(R.color.black))
            ll_months.visibility = View.VISIBLE
            ll_months_data.visibility = View.VISIBLE
            ll_week.visibility = View.GONE
            ll_weeks_data.visibility = View.GONE
        }
    }

    override fun createPresenter() = AnalyticsShopPresenter(requireContext())

    override fun preLoadIntent() = loadTrigger

    override fun render(state: AnalyticsShopState) {
        when(state){
            is AnalyticsShopState.MainState -> {

            }
            is AnalyticsShopState.Loading -> {

            }
            is AnalyticsShopState.ShowErrorMessage -> {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(isFirstRun){
            loadTrigger.onNext(1)
            isFirstRun=false
        }
    }
    companion object{
        @JvmStatic
        fun newInstance() = AnalyticsShopFragment()
    }




}