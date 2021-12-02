package kz.xbase.mstroy.fragments.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_home.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.*
import kz.xbase.mstroy.presenters.HomePresenter
import kz.xbase.mstroy.states.HomeState
import kz.xbase.mstroy.utils.OnSwipeListener
import kz.xbase.mstroy.views.HomeFragmentView
import java.util.*

class HomeFragment : MviFragment<HomeFragmentView,HomePresenter>(), HomeFragmentView{
    private lateinit var preLoadTrigger : PublishSubject<Int>
    var isFirstRun = true
    var day =0
    var dayCards = listOf<MaterialCardView>()
    var tvDayCards = listOf<TextView>()
    var pointerDayCards = listOf<MaterialCardView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preLoadTrigger = PublishSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_photo.setImage("https://www.radidomapro.ru/images/unique/585-640-20180219_110549_585-640-20170515131741lerua.jpg")
        dayCards = listOf<MaterialCardView>(cv1,cv2,cv3,cv4,cv5,cv6,cv7)
        tvDayCards = listOf<TextView>(tv1,tv2,tv3,tv4,tv5,tv6,tv7)
        pointerDayCards = listOf<MaterialCardView>(cv_1,cv_2,cv_3,cv_4,cv_5,cv_6,cv_7)
        val calendar = Calendar.getInstance()
        day=if(day==0){ 6 }else {day-1}
        dayCards[day].setCardBackgroundColor(resources.getColor(R.color.white))
        tvDayCards[day].setTextColor(resources.getColor(R.color.black))
        pointerDayCards[day].setCardBackgroundColor(resources.getColor(R.color.white))
        Log.d("SukIn",day.toString())
        setListeners()
    }

    private fun setListeners(){
        cv_right.setOnClickListener {
            try {
                Log.d("Suk",day.toString())
                if(day+1!=7) {
                    tv_time.outToLeftAnimation()
                    day++
                    Handler().postDelayed(Runnable {
                        tv_time.text = day.toString()
                        tv_time.inFromRightAnimation()
                        for (i in 0..6){
                            dayCards[i].setCardBackgroundColor(resources.getColor(R.color.grey_card))
                            tvDayCards[i].setTextColor(resources.getColor(R.color.grey))
                            pointerDayCards[i].setCardBackgroundColor(resources.getColor(R.color.grey_card))
                        }
                        dayCards[day].setCardBackgroundColor(resources.getColor(R.color.white))
                        tvDayCards[day].setTextColor(resources.getColor(R.color.black))
                        pointerDayCards[day].setCardBackgroundColor(resources.getColor(R.color.white))
                    }, 500)
                }
            }catch (ex:Exception){
            }

        }
        cv_left.setOnClickListener {
            try {
                Log.d("Suk",day.toString())
                if(day-1!=-1) {
                    tv_time.outToRightAnimation()
                    day--
                    Handler().postDelayed(Runnable {
                        tv_time.text = day.toString()
                        tv_time.inFromLeftAnimation()
                        for (i in 0..6){
                            dayCards[i].setCardBackgroundColor(resources.getColor(R.color.grey_card))
                            tvDayCards[i].setTextColor(resources.getColor(R.color.grey))
                            pointerDayCards[i].setCardBackgroundColor(resources.getColor(R.color.grey_card))
                        }
                        dayCards[day].setCardBackgroundColor(resources.getColor(R.color.white))
                        tvDayCards[day].setTextColor(resources.getColor(R.color.black))
                        pointerDayCards[day].setCardBackgroundColor(resources.getColor(R.color.white))
                    }, 500)
                }
            }catch (ex:Exception){

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun createPresenter() = HomePresenter(requireContext())

    override fun preLoadIntent() = preLoadTrigger

    override fun onResume() {
        super.onResume()
        if(isFirstRun) {
            preLoadTrigger.onNext(1)
            isFirstRun=false
        }
    }

    override fun render(state: HomeState) {
        when(state){
            is HomeState.MainState -> {
                ll_main.visibility = View.VISIBLE
                progress.visibility = View.GONE
            }
            is HomeState.Loading -> {
                ll_main.visibility = View.GONE
                progress.visibility = View.VISIBLE
            }
            is HomeState.ShowErrorMessage -> {
                ll_main.visibility = View.GONE
                progress.visibility = View.VISIBLE
                ll_main.showMessage(state.error)
            }
        }
    }


}