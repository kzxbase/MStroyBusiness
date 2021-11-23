package kz.xbase.mstroy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.subjects.PublishSubject
import kz.xbase.mstroy.R
import kz.xbase.mstroy.presenters.RegisterBusinessPresenter
import kz.xbase.mstroy.states.RegisterBusinessState
import kz.xbase.mstroy.views.RegisterBusinessView

class RegisterBusinessFragment : MviFragment<RegisterBusinessView,RegisterBusinessPresenter>(),RegisterBusinessView {
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