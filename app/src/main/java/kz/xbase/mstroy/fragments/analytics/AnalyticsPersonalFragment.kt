package kz.xbase.mstroy.fragments.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_analytics_personal.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.HomeActivity
import kz.xbase.mstroy.adapters.PersonalAdapter
import kz.xbase.mstroy.presenters.AnalyticsPersonalPresenter
import kz.xbase.mstroy.states.AnalyticsPersonalState
import kz.xbase.mstroy.views.AnalyticsPersonalView
import okio.JvmStatic

class AnalyticsPersonalFragment : MviFragment<AnalyticsPersonalView,AnalyticsPersonalPresenter>(), AnalyticsPersonalView {
    private lateinit var preLoadTrigger : PublishSubject<Int>
    private var personalList:ArrayList<String> = arrayListOf()
    private val personalAdapter by lazy { PersonalAdapter(requireContext(),personalList) }
    var isFirstRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preLoadTrigger = PublishSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analytics_personal,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_personal.apply {
            adapter = personalAdapter
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        }
        personalAdapter.onItemClick ={
            (activity as HomeActivity).navigateAnalyticsPersonalItemFragment()
        }
        personalList.add("")
        personalList.add("")
        personalList.add("")
        personalList.add("")
        personalList.add("")
        personalList.add("")
    }

    override fun createPresenter() = AnalyticsPersonalPresenter(requireContext())

    override fun preLoadIntent() = preLoadTrigger

    override fun render(state: AnalyticsPersonalState) {
        when(state){
            is AnalyticsPersonalState.MainState -> {

            }
            is AnalyticsPersonalState.ShowErrorMessage -> {

            }
            is AnalyticsPersonalState.Loading -> {

            }
        }
    }
    companion object{
        @JvmStatic
        fun newInstance() = AnalyticsPersonalFragment()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstRun){
            preLoadTrigger.onNext(1)
            isFirstRun = false
        }
    }

}