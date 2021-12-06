package kz.xbase.mstroy.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_cards.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.HomeActivity
import kz.xbase.mstroy.activity.utils.showToast
import kz.xbase.mstroy.adapters.CardsAdapter
import kz.xbase.mstroy.presenters.CardsPresenter
import kz.xbase.mstroy.states.CardsState
import kz.xbase.mstroy.views.CardsView

class CardsFragment : MviFragment<CardsView,CardsPresenter>(),CardsView {
    private lateinit var loadTrigger : PublishSubject<Int>
    private lateinit var chooseTrigger : PublishSubject<String>
    private var cardsList:ArrayList<String> = arrayListOf()
    private val cardsAdapter by lazy { CardsAdapter(requireContext(),cardsList) }
    var isFirstRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadTrigger = PublishSubject.create()
        chooseTrigger = PublishSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cards,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_cards.apply {
            adapter = cardsAdapter
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        }
        cardsList.add("5746")
        cardsList.add("8786")
        cardsList.add("8796")
        cardsList.add("7896")
    }


    override fun createPresenter() = CardsPresenter(requireContext())

    override fun chooseIntent() = chooseTrigger

    override fun loadIntent() = loadTrigger

    override fun render(state: CardsState) {
        when(state) {
            is CardsState.MainState -> {
                progress.visibility = View.GONE
                ll_main.visibility = View.VISIBLE
            }
            is CardsState.Loading -> {
                progress.visibility = View.VISIBLE
                ll_main.visibility = View.GONE
            }
            is CardsState.ShowErrorMessage -> {
                progress.visibility = View.GONE
                ll_main.visibility = View.VISIBLE
            }
            is CardsState.ChoosedState -> {
                showToast("Основная карта сменилась")
                (activity as HomeActivity).navigateHomeFragment()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CardsFragment()
    }

    override fun onResume() {
        super.onResume()
        if(isFirstRun){
            loadTrigger.onNext(1)
            isFirstRun = false
        }
    }

}