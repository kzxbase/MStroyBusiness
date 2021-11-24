package kz.xbase.mstroy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_register_business.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.adapters.RegisterPhotoAdapter
import kz.xbase.mstroy.presenters.RegisterBusinessPresenter
import kz.xbase.mstroy.states.RegisterBusinessState
import kz.xbase.mstroy.views.RegisterBusinessView

class RegisterBusinessFragment : MviFragment<RegisterBusinessView,RegisterBusinessPresenter>(),RegisterBusinessView {
    var isCash = false
    var isTerminal = false
    var isSchet = false
    var isKaspi = false
    private var photoList:ArrayList<String> = arrayListOf()
    private val photoAdapter by lazy { RegisterPhotoAdapter(requireContext(),photoList) }

    private lateinit var uploadDataTrigger : PublishSubject<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadDataTrigger = PublishSubject.create()
    }

    companion object {
        @JvmStatic
        fun newInstance(data:String) = RegisterBusinessFragment().apply {
            arguments = Bundle().apply {
                putSerializable("data",data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_business,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_images.apply {
            adapter = photoAdapter
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        }
        photoList.add("")
        photoAdapter.notifyDataSetChanged()
        setListeners()
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

        }
        ll_kaspi.setOnClickListener {
            if (isKaspi){
                isKaspi=false
                iv_kaspi.setImageDrawable(resources.getDrawable(R.drawable.ic_unchecked))
            }else{
                isKaspi=true
                iv_kaspi.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
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
        }
        ll_schet.setOnClickListener {
            if(isSchet){
                isSchet=false
                iv_schet.setImageDrawable(resources.getDrawable(R.drawable.ic_unchecked))
            }else{
                isSchet=true
                iv_schet.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))
            }
        }
    }

    override fun createPresenter() = RegisterBusinessPresenter(requireContext())

    override fun uploadDataIntent() = uploadDataTrigger

    override fun render(state: RegisterBusinessState) {
        when(state){
            is RegisterBusinessState.MainState -> {

            }
            is RegisterBusinessState.SuccessState -> {

            }
            is RegisterBusinessState.Loading -> {

            }
            is RegisterBusinessState.ShowErrorMessage -> {

            }
        }
    }

}