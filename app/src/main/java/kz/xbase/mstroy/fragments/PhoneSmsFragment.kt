package kz.xbase.mstroy.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_phone_sms.*
import kotlinx.android.synthetic.main.fragment_phone_sms.btn_next
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.closeKeyboard
import kz.xbase.mstroy.presenters.PhoneSmsPresenter
import kz.xbase.mstroy.states.PhoneSmsState
import kz.xbase.mstroy.views.PhoneSmsView

class PhoneSmsFragment : MviFragment<PhoneSmsView,PhoneSmsPresenter>(),PhoneSmsView {

    private lateinit var checkSmsTrigger : PublishSubject<String>
    private lateinit var resendTrigger : PublishSubject<String>
    private lateinit var phone: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            phone = it.getSerializable("phone") as String
        }
        return inflater.inflate(R.layout.fragment_phone_sms,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        if(this::phone.isInitialized){
            tv_description.text = "На номер "+phone.replace(" |","")+" отправлен код"
        }
    }
    private fun setListeners(){
        edt_pin.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(s!=null && s.length==4){
                    edt_pin.closeKeyboard()
                    btn_next.isEnabled=true
                    btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                }else{
                    btn_next.isEnabled=false
                    btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                }
            }

        })
    }

    override fun createPresenter() = PhoneSmsPresenter(requireContext())

    override fun checkSmsIntent() = checkSmsTrigger

    override fun resendIntent() = resendTrigger

    override fun render(state: PhoneSmsState) {
        when(state){
            is PhoneSmsState.MainState -> {
                edt_pin.text?.clear()
                progress.visibility = View.GONE
                tv_time_left.visibility = View.GONE
                btn_next.isEnabled = false

            }
            is PhoneSmsState.SmsSentState -> {
                edt_pin.text?.clear()
                tv_time_left.visibility = View.GONE
                btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
            }
            is PhoneSmsState.correctSmsState -> {

            }
            is PhoneSmsState.Loading -> {
                progress.visibility = View.VISIBLE
                btn_next.visibility = View.GONE
            }
            is PhoneSmsState.ResendAvailableState -> {
                tv_time_left.visibility = View.VISIBLE
            }
            is PhoneSmsState.ShowErrorMessage -> {

            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(phone:String) = PhoneSmsFragment().apply {
            arguments = Bundle().apply {
                putSerializable("phone",phone)
            }
        }
    }

}