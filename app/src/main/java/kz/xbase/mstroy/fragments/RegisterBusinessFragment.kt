package kz.xbase.mstroy.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_phone_sms.*
import kotlinx.android.synthetic.main.fragment_register_business.*
import kotlinx.android.synthetic.main.fragment_register_business.btn_next
import kotlinx.android.synthetic.main.fragment_register_business.progress
import kz.xbase.a_pay.utils.PhoneTextWatcher
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.LoginActivity
import kz.xbase.mstroy.adapters.RegisterPhotoAdapter
import kz.xbase.mstroy.presenters.RegisterBusinessPresenter
import kz.xbase.mstroy.states.RegisterBusinessState
import kz.xbase.mstroy.views.RegisterBusinessView

class RegisterBusinessFragment : MviFragment<RegisterBusinessView,RegisterBusinessPresenter>(),RegisterBusinessView {
    var isCash = false
    var isTerminal = false
    var isSchet = false
    var isKaspi = false
    private var photoList:ArrayList<Uri> = arrayListOf()
    private val photoAdapter by lazy { RegisterPhotoAdapter(requireContext(),photoList) }
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>
    private lateinit var uploadDataTrigger : PublishSubject<String>
    private lateinit var data: String
    private var isPerson = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadDataTrigger = PublishSubject.create()
    }

    companion object {
        @JvmStatic
        fun newInstance(data:String,isPerson:Boolean) = RegisterBusinessFragment().apply {
            arguments = Bundle().apply {
                putSerializable("data",data)
                putBoolean("isPerson",isPerson)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            data = it.getSerializable("data") as String
            isPerson = it.getBoolean("isPerson")
        }
        return inflater.inflate(R.layout.fragment_register_business,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_images.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        }
        setListeners()
        initView()
    }

    private fun initView() {
        if(isPerson){
            ll_city.visibility = View.GONE
            ll_adress.visibility = View.GONE
            tv_phone_desc.text = "Номер телефона*"
            edt_phone.hint = "Номер телефона"
            ll_location.visibility = View.GONE
            ll_names.visibility = View.GONE
            ll_payments.visibility = View.GONE
            tv_photo_desc.text = "Добавить фото(необязательно)"
        }
    }

    private fun setListeners() {
        ll_terminal.setOnClickListener {
            if(isTerminal){
                isTerminal=false
                iv_terminal.setImageDrawable(resources.getDrawable(R.drawable.ic_unchecked))
            }else{
                isTerminal=true
                iv_terminal.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
            }
            if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                && edt_phone.text.toString().isNotEmpty() && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                btn_next.isEnabled = true
                btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
            }else{
                btn_next.isEnabled = false
                btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
            }
        }
        ll_kaspi.setOnClickListener {
            if (isKaspi){
                isKaspi=false
                iv_kaspi.setImageDrawable(resources.getDrawable(R.drawable.ic_unchecked))
            }else{
                isKaspi=true
                iv_kaspi.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
            }
            if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                && edt_phone.text.toString().isNotEmpty() && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                btn_next.isEnabled = true
                btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
            }else{
                btn_next.isEnabled = false
                btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
            }
        }
        ll_cash.setOnClickListener {
            if(isCash){
                isCash=false
                iv_cash.setImageDrawable(resources.getDrawable(R.drawable.ic_unchecked))
            }else{
                isCash=true
                iv_cash.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
            }
            if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                && edt_phone.text.toString().isNotEmpty() && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                btn_next.isEnabled = true
                btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
            }else{
                btn_next.isEnabled = false
                btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
            }
        }
        ll_schet.setOnClickListener {
            if(isSchet){
                isSchet=false
                iv_schet.setImageDrawable(resources.getDrawable(R.drawable.ic_unchecked))
            }else{
                isSchet=true
                iv_schet.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
            }
            if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                && edt_phone.text.toString().isNotEmpty() && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                btn_next.isEnabled = true
                btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
            }else{
                btn_next.isEnabled = false
                btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
            }
        }
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                it.data?.data?.let { it1 ->
                    photoList.add(it1)
                    photoAdapter.notifyDataSetChanged()
                }
            }
        }
        cv_camera.setOnClickListener{
            choosePhoto()
        }
        btn_next.setOnClickListener {
            uploadDataTrigger.onNext("")
        }
        edt_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isPerson) {
                    if (edt_name.text.toString().isNotEmpty() && edt_surname.text.toString()
                            .isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString()
                            .isNotEmpty()
                    ) {
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }
                if(!isPerson){
                    if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                        && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }

            }

        })
        edt_surname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isPerson) {
                    if (edt_name.text.toString().isNotEmpty() && edt_surname.text.toString()
                            .isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString()
                            .isNotEmpty()
                    ) {
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }
                if(!isPerson){
                    if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                        && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }

            }

        })
        edt_adress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isPerson) {
                    if (edt_name.text.toString().isNotEmpty() && edt_surname.text.toString()
                            .isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString()
                            .isNotEmpty()
                    ) {
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }
                if(!isPerson){
                    if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                        && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }

            }

        })
        edt_business_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isPerson) {
                    if (edt_name.text.toString().isNotEmpty() && edt_surname.text.toString()
                            .isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString()
                            .isNotEmpty()
                    ) {
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }
                if(!isPerson){
                    if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                        && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }

            }

        })
        edt_short_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isPerson) {
                    if (edt_name.text.toString().isNotEmpty() && edt_surname.text.toString()
                            .isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString()
                            .isNotEmpty()
                    ) {
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }
                if(!isPerson){
                    if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                        && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }

            }

        })
        edt_id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isPerson) {
                    if (edt_name.text.toString().isNotEmpty() && edt_surname.text.toString()
                            .isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString()
                            .isNotEmpty()
                    ) {
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }
                if(!isPerson){
                    if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                        && edt_phone.text.length==18 && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                        && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                        btn_next.isEnabled = true
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                    }else{
                        btn_next.isEnabled = false
                        btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                    }
                }

            }

        })
        val phoneWatcher = PhoneTextWatcher(edt_phone)
        edt_phone.addTextChangedListener(phoneWatcher)
        phoneWatcher.isFull = {
            if(isPerson) {
                if (edt_name.text.toString().isNotEmpty() && edt_surname.text.toString()
                        .isNotEmpty()
                    && it && edt_id.text.toString()
                        .isNotEmpty()
                ) {
                    btn_next.isEnabled = true
                    btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                }else{
                    btn_next.isEnabled = false
                    btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                }
            }
            if(!isPerson){
                if(edt_name.text.toString().isNotEmpty() && edt_surname.text.toString().isNotEmpty()
                    && it && edt_id.text.toString().isNotEmpty() && (isSchet || isCash || isKaspi || isTerminal)
                    && edt_adress.text.toString().isNotEmpty() && edt_business_name.text.toString().isNotEmpty() && edt_short_name.text.toString().isNotEmpty()){
                    btn_next.isEnabled = true
                    btn_next.backgroundTintList = resources.getColorStateList(R.color.white)
                }else{
                    btn_next.isEnabled = false
                    btn_next.backgroundTintList = resources.getColorStateList(R.color.grey)
                }
            }
        }
    }
    private fun choosePhoto(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    override fun createPresenter() = RegisterBusinessPresenter(requireContext())

    override fun uploadDataIntent() = uploadDataTrigger

    override fun render(state: RegisterBusinessState) {
        when(state){
            is RegisterBusinessState.MainState -> {
                progress.visibility = View.GONE
                btn_next.visibility = View.VISIBLE
            }
            is RegisterBusinessState.SuccessState -> {
                (activity as LoginActivity).navigateRegisterPassFragment()
            }
            is RegisterBusinessState.Loading -> {
                progress.visibility = View.VISIBLE
                btn_next.visibility = View.GONE
            }
            is RegisterBusinessState.ShowErrorMessage -> {
                progress.visibility = View.GONE
                btn_next.visibility = View.VISIBLE
            }
        }
    }

}