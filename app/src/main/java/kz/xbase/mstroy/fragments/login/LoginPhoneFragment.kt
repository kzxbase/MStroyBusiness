package kz.xbase.mstroy.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_login_phone.*
import kz.xbase.a_pay.utils.PhoneTextWatcher
import kz.xbase.a_pay.utils.TextConverter
import kz.xbase.a_pay.views.LoginPhoneView
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.LoginActivity
import kz.xbase.mstroy.activity.utils.closeKeyboard
import kz.xbase.mstroy.presenters.LoginPhonePresenter
import kz.xbase.mstroy.states.LoginPhoneState


class LoginPhoneFragment : MviFragment<LoginPhoneView,LoginPhonePresenter>(),LoginPhoneView {

    private lateinit var checkHasUserTrigger:PublishSubject<String>
    private lateinit var mainTrigger:PublishSubject<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkHasUserTrigger = PublishSubject.create()
        mainTrigger = PublishSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login_phone,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

    }
    private fun setListeners(){
        val phoneTextWatcher = PhoneTextWatcher(edt_phone)
        edt_phone.addTextChangedListener(phoneTextWatcher)
        phoneTextWatcher.isFull={
            if(it){
                btn_next.isEnabled=true
                btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                edt_phone.closeKeyboard()
            }else{
                btn_next.isEnabled=false
                btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
            }
        }
        btn_next.setOnClickListener {
            checkHasUserTrigger.onNext(TextConverter().getOnlyDigits(edt_phone.text.toString()))
        }
    }

    override fun createPresenter() = LoginPhonePresenter(requireContext())

    override fun checkHasUserIntent() = checkHasUserTrigger

    override fun goMainIntent() = mainTrigger

    override fun render(state: LoginPhoneState) {
        when(state){
            is LoginPhoneState.Loading -> {
                progress.visibility = View.VISIBLE
                edt_phone.isEnabled = false
                btn_next.isEnabled = false
            }
            is LoginPhoneState.MainState -> {
                edt_phone.text.clear()
                edt_phone.isEnabled = true
                btn_next.isEnabled = false
                progress.visibility = View.GONE
            }
            is LoginPhoneState.HasUserState -> {
                mainTrigger.onNext(0)
                if(state.isRegistered){
                    (requireActivity() as LoginActivity).navigateLoginPhonePassFragment(edt_phone.text.toString())
                }else{
                    (requireActivity() as LoginActivity).navigatePhoneSmsFragment(edt_phone.text.toString())
                }
            }
            is LoginPhoneState.ShowErrorMessage -> {

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginPhoneFragment()
    }
}