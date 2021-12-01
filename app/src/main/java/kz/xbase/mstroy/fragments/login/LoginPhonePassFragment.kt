package kz.xbase.mstroy.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_login_phonepass.*
import kotlinx.android.synthetic.main.fragment_login_phonepass.edt_phone
import kz.xbase.a_pay.utils.PhoneTextWatcher
import kz.xbase.a_pay.utils.TextConverter
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.LoginActivity
import kz.xbase.mstroy.activity.RegisterPinActivity
import kz.xbase.mstroy.model.mvi.AuthModel
import kz.xbase.mstroy.presenters.LoginPhonePassPresenter
import kz.xbase.mstroy.states.LoginPhonePassState
import kz.xbase.mstroy.views.LoginPhonePassView

class LoginPhonePassFragment : MviFragment<LoginPhonePassView,LoginPhonePassPresenter>(),LoginPhonePassView {
    private lateinit var checkPhonePassTrigger : PublishSubject<AuthModel>
    private lateinit var mainTrigger : PublishSubject<Int>
    private lateinit var phone:String

    companion object {
        @JvmStatic
        fun newInstance(phone:String) = LoginPhonePassFragment().apply {
            arguments = Bundle().apply {
                putSerializable("phone",phone)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPhonePassTrigger = PublishSubject.create()
        mainTrigger = PublishSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login_phonepass,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners(){
        val phoneTextWatcher = PhoneTextWatcher(edt_phone)
        edt_phone.addTextChangedListener(phoneTextWatcher)
        arguments?.let {
            phone = it.getSerializable("phone") as String
        }
        if(!phone.isNullOrEmpty()) {
            edt_phone.setText(phone)
        }
        btn_signin.setOnClickListener {
            checkPhonePassTrigger.onNext(AuthModel(TextConverter().getOnlyDigits(edt_phone.text.toString()),edt_pass.text.toString()))
        }
        edt_pass.addTextChangedListener {
            if(it!=null && it.isNotEmpty()){
                btn_signin.isEnabled=true
                btn_signin.backgroundTintList = resources.getColorStateList(R.color.white)
            }
        }
        btn_forgot.setOnClickListener {
            (requireActivity() as LoginActivity).navigateLoginForgotFragment(edt_phone.text.toString())
        }
    }

    override fun createPresenter() = LoginPhonePassPresenter(requireContext())

    override fun checkPhonePassIntent() = checkPhonePassTrigger

    override fun goMainIntent() = mainTrigger

    override fun render(state: LoginPhonePassState) {
        when(state){
            is LoginPhonePassState.MainState -> {
                progress.visibility = View.GONE
                btn_signin.visibility = View.VISIBLE
                edt_pass.setText("")
            }
            is LoginPhonePassState.Loading -> {
                btn_signin.visibility = View.GONE
                progress.visibility = View.GONE
            }
            is LoginPhonePassState.SuccessState -> {
                mainTrigger.onNext(1)
                startActivity(Intent(requireContext(),RegisterPinActivity::class.java))
                requireActivity().finish()
            }
            is LoginPhonePassState.ShowErrorMessage -> {

            }
        }
    }

}