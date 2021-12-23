package kz.xbase.mstroy.fragments.login

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_register_business.*
import kotlinx.android.synthetic.main.item_register_photo.view.*
import kz.xbase.a_pay.utils.PhoneTextWatcher
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.LoginActivity
import kz.xbase.mstroy.activity.utils.replace
import kz.xbase.mstroy.activity.utils.showToast
import kz.xbase.mstroy.adapters.RegisterPhotoAdapter
import kz.xbase.mstroy.bottomSheets.RegisterMapBottomSheet
import kz.xbase.mstroy.model.mvi.RegisterModel
import kz.xbase.mstroy.model.network.City
import kz.xbase.mstroy.presenters.RegisterBusinessPresenter
import kz.xbase.mstroy.states.RegisterBusinessState
import kz.xbase.mstroy.utils.OnLocationPermission
import kz.xbase.mstroy.utils.SessionManager
import kz.xbase.mstroy.views.RegisterBusinessView

class RegisterBusinessFragment : Fragment() {

    private lateinit var resultLauncher:ActivityResultLauncher<Intent>
    private var isPerson = false
    private var cityList:List<City> = listOf()
    private var photoNum = 0
    var iviAboutDialog:RegisterMapBottomSheet?=null

    companion object {
        @JvmStatic
        fun newInstance(cityList: List<City>) = RegisterBusinessFragment().apply {
            arguments = Bundle().apply {
                putSerializable("cityList",Gson().toJson(cityList))
            }
        }
        var location = Location("")
        var locationName = ""
        var isCash = false
        var isTerminal = false
        var isSchet = false
        var isKaspi = false
        private var mainPhoto:Uri? = null
        private var firstPhoto:Uri? = null
        private var secondPhoto:Uri? = null
        private var thirdPhoto:Uri? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            cityList = Gson().fromJson(it.getSerializable("cityList") as String,
                object : TypeToken<ArrayList<City>>() {}.type)
        }
        isPerson = !SessionManager(requireContext()).role.equals("MBusiness owner")
        return inflater.inflate(R.layout.fragment_register_business,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        initView()
        btn_location.text = locationName.ifEmpty { "Отметка на карте" }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, cityList.map { it.nameRus })
        val listPopupWindow = ListPopupWindow(requireContext(), null, androidx.appcompat.R.attr.listPopupWindowStyle)
        listPopupWindow.anchorView = cv_city
        listPopupWindow.setAdapter(adapter)
        listPopupWindow.setOnItemClickListener { parent, view, position, id ->
            tv_city.text = cityList[position].nameRus
            listPopupWindow.dismiss()
        }
        cv_city.setOnClickListener {
            listPopupWindow.show()
        }
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
        btn_location.setOnClickListener {
            iviAboutDialog = RegisterMapBottomSheet.newInstance()
            iviAboutDialog?.replace(fragmentManager,true)
        }
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
                    var photos=requireContext().getExternalFilesDir("Pictures")
                    if(photos!=null) {
                        if (photoNum==0){
                            mainPhoto = it1
                            iv_photo.setImageURI(mainPhoto)
                        }else if(photoNum==1){
                            firstPhoto = it1
                            iv_photo_first.setImageURI(firstPhoto)
                        }else if(photoNum==2){
                            secondPhoto = it1
                            iv_photo_two.setImageURI(secondPhoto)
                        }else{
                            thirdPhoto = it1
                            iv_photo_three.setImageURI(thirdPhoto)
                        }
                    }
                }
            }
        }
        cv_camera_main.setOnClickListener{
            photoNum = 0
            if(mainPhoto!=null){
                removePhoto()
            }else {
                choosePhoto()
            }
        }
        cv_camera_first.setOnClickListener{
            photoNum =1
            if(firstPhoto!=null){
                removePhoto()
            }else {
                choosePhoto()
            }
        }
        cv_camera_two.setOnClickListener{
            photoNum =2
            if(secondPhoto!=null){
                removePhoto()
            }else {
                choosePhoto()
            }
        }
        cv_camera_three.setOnClickListener{
            photoNum =3
            if(thirdPhoto!=null){
                removePhoto()
            }else {
                choosePhoto()
            }
        }
        btn_next.setOnClickListener {
            (activity as LoginActivity).navigateRegisterPassFragment(RegisterModel(mainPhoto,firstPhoto,secondPhoto,thirdPhoto,""))
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
    private fun removePhoto(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Да",object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if(photoNum==0){
                    mainPhoto=null
                    iv_photo.setImageDrawable(resources.getDrawable(R.drawable.ic_camera))
                }else if(photoNum==1){
                    firstPhoto=null
                    iv_photo_first.setImageDrawable(resources.getDrawable(R.drawable.ic_camera))
                }else if(photoNum==2){
                    secondPhoto=null
                    iv_photo_two.setImageDrawable(resources.getDrawable(R.drawable.ic_camera))
                }else{
                    thirdPhoto=null
                    iv_photo_three.setImageDrawable(resources.getDrawable(R.drawable.ic_camera))
                }
            }
        })
        builder.setNegativeButton("Отмена",object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }
        })
        builder.setTitle("Хотите удалить фото ?")
        builder.show()
    }

}