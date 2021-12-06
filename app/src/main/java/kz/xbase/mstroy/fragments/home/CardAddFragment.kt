package kz.xbase.mstroy.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_card_add.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.showMessage
import kz.xbase.mstroy.activity.utils.showToast
import kz.xbase.mstroy.presenters.CardAddPresenter
import kz.xbase.mstroy.states.CardAddState
import kz.xbase.mstroy.views.CardAddView

class CardAddFragment : MviFragment<CardAddView,CardAddPresenter>(),CardAddView {
    private lateinit var addTrigger : PublishSubject<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTrigger = PublishSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_add,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_add.setOnClickListener {
            addTrigger.onNext("")
        }
    }

    override fun createPresenter() = CardAddPresenter(requireContext())

    override fun addIntent() = addTrigger

    override fun render(state: CardAddState) {
        when(state){
            is CardAddState.SuccessState -> {
                showToast("Карта успешно добавлена")

            }
            is CardAddState.Loading -> {
                progress.visibility = View.VISIBLE
                ll_main.visibility = View.GONE
            }
            is CardAddState.ShowErrorMessage -> {
                ll_main.showMessage(state.error)
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = CardAddFragment()
    }

}