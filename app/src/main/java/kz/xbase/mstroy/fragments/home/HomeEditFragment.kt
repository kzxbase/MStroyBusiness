package kz.xbase.mstroy.fragments.home

import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import com.google.android.material.card.MaterialCardView
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_home_edit.*
import kotlinx.android.synthetic.main.fragment_home_edit.cv1
import kotlinx.android.synthetic.main.fragment_home_edit.cv2
import kotlinx.android.synthetic.main.fragment_home_edit.cv3
import kotlinx.android.synthetic.main.fragment_home_edit.cv4
import kotlinx.android.synthetic.main.fragment_home_edit.cv5
import kotlinx.android.synthetic.main.fragment_home_edit.cv6
import kotlinx.android.synthetic.main.fragment_home_edit.cv7
import kotlinx.android.synthetic.main.fragment_home_edit.cv_1
import kotlinx.android.synthetic.main.fragment_home_edit.cv_2
import kotlinx.android.synthetic.main.fragment_home_edit.cv_3
import kotlinx.android.synthetic.main.fragment_home_edit.cv_4
import kotlinx.android.synthetic.main.fragment_home_edit.cv_5
import kotlinx.android.synthetic.main.fragment_home_edit.cv_6
import kotlinx.android.synthetic.main.fragment_home_edit.cv_7
import kotlinx.android.synthetic.main.fragment_home_edit.iv_photo
import kotlinx.android.synthetic.main.fragment_home_edit.ll_main
import kotlinx.android.synthetic.main.fragment_home_edit.progress
import kotlinx.android.synthetic.main.fragment_home_edit.tv1
import kotlinx.android.synthetic.main.fragment_home_edit.tv2
import kotlinx.android.synthetic.main.fragment_home_edit.tv3
import kotlinx.android.synthetic.main.fragment_home_edit.tv4
import kotlinx.android.synthetic.main.fragment_home_edit.tv5
import kotlinx.android.synthetic.main.fragment_home_edit.tv6
import kotlinx.android.synthetic.main.fragment_home_edit.tv7
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.HomeActivity
import kz.xbase.mstroy.activity.utils.*
import kz.xbase.mstroy.presenters.HomeEditPresenter
import kz.xbase.mstroy.states.HomeEditState
import kz.xbase.mstroy.views.HomeEditView
import java.util.*

class HomeEditFragment : MviFragment<HomeEditView, HomeEditPresenter>(), HomeEditView {
    private lateinit var editTrigger : PublishSubject<String>
    var day =0
    var dayCards = listOf<MaterialCardView>()
    var tvDayCards = listOf<TextView>()
    var pointerDayCards = listOf<MaterialCardView>()
    var isFinishTime = false
    private lateinit var timePickerDialog: TimePickerDialog
    companion object {
        @JvmStatic
        fun newInstance() = HomeEditFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editTrigger = PublishSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_edit,container,false)
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
        btn_confirm_edit.setOnClickListener {
            editTrigger.onNext("")
        }
        timePickerDialog = TimePickerDialog(requireContext(),R.style.dialog,
            { _, hourOfDay, minute ->
                if(!isFinishTime) {
                    isFinishTime=true
                    timePickerDialog.setMessage("Окончание работы")
                    Handler().postDelayed(Runnable {
                        timePickerDialog.show() }, 200)
                }
            }
            ,9,0,true)

        cv_time.setOnClickListener {
            isFinishTime=false
            timePickerDialog.setMessage("Начало работы")
            timePickerDialog.show()
        }
    }

    override fun createPresenter() = HomeEditPresenter (requireContext())

    override fun editIntent() = editTrigger

    override fun render(state: HomeEditState) {
        when(state){
            is HomeEditState.Loading -> {
                progress.visibility = View.VISIBLE
                ll_main.visibility = View.GONE
            }
            is HomeEditState.SuccessState -> {
                showToast("Данные успешно обновлены")
                (activity as HomeActivity).navigateHomeFragment()
            }
            is HomeEditState.ShowErrorMessage -> {
                progress.showMessage(state.error)
                progress.visibility = View.GONE
                ll_main.visibility = View.VISIBLE
            }
        }
    }
}