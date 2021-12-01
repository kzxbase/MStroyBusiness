package kz.xbase.mstroy.fragments.login

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_register_password.*
import kotlinx.android.synthetic.main.fragment_register_password.btn_next
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.RegisterPinActivity
import kz.xbase.mstroy.activity.utils.showMessage
import kz.xbase.mstroy.model.mvi.NewPassModel
import kz.xbase.mstroy.presenters.RegisterPasswordPresenter
import kz.xbase.mstroy.states.RegisterPasswordState
import kz.xbase.mstroy.views.RegisterPasswordView

class RegisterPasswordFragment : MviFragment<RegisterPasswordView,RegisterPasswordPresenter>(),RegisterPasswordView {
    private lateinit var setPassTrigger:PublishSubject<NewPassModel>
    var passVisible = false
    var passVisible2 = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPassTrigger = PublishSubject.create()
    }
    companion object {
        @JvmStatic
        fun newInstance() = RegisterPasswordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_password,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        iv_pass_visibility.setOnClickListener {
            if (!passVisible) {
                edt_pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                iv_pass_visibility.setImageResource(R.drawable.ic_visible)
                passVisible=true
            }else{
                passVisible=false
                edt_pass.transformationMethod = PasswordTransformationMethod.getInstance()
                iv_pass_visibility.setImageResource(R.drawable.ic_not_visible)
            }
        }
        iv_pass_visibility2.setOnClickListener {
            if (!passVisible2) {
                passVisible2=true
                edt_pass2.transformationMethod = HideReturnsTransformationMethod.getInstance()
                iv_pass_visibility2.setImageResource(R.drawable.ic_visible)
            }else{
                passVisible2=false
                edt_pass2.transformationMethod = PasswordTransformationMethod.getInstance()
                iv_pass_visibility2.setImageResource(R.drawable.ic_not_visible)
            }
        }
        edt_pass.addTextChangedListener {
            if(edt_pass.text.length>7 && edt_pass2.text.length>7){
                btn_next.isEnabled=true
                btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
            }else{
                btn_next.isEnabled=false
                btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
            }
        }
        edt_pass2.addTextChangedListener {
            if(edt_pass.text.length>7 && edt_pass2.text.length>7){
                btn_next.isEnabled=true
                btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
            }else{
                btn_next.isEnabled=false
                btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
            }
        }
        btn_next.setOnClickListener {
            if(edt_pass.text.toString().equals(edt_pass2.text.toString())) {
                setPassTrigger.onNext(
                    NewPassModel(
                        edt_pass.text.toString(),
                        edt_pass2.text.toString()
                    )
                )
            }else{
                btn_next.showMessage("Пароли не совпадают!")
            }
        }

    }

    override fun createPresenter() = RegisterPasswordPresenter(requireContext())

    override fun setPasswordIntent() = setPassTrigger

    override fun render(state: RegisterPasswordState) {
        when(state){
            is RegisterPasswordState.SuccesState -> {
                startActivity(Intent(requireActivity(),RegisterPinActivity::class.java))
                requireActivity().finish()
            }
            is RegisterPasswordState.Loading -> {
                progress.visibility = View.VISIBLE
                btn_next.visibility = View.GONE
            }
            is RegisterPasswordState.ShowErrorMessage -> {
                progress.visibility = View.GONE
                btn_next.visibility = View.VISIBLE
            }
        }
    }

}