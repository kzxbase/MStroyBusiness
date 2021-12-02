package kz.xbase.mstroy.fragments.home

import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import kz.xbase.mstroy.presenters.HomeEditPresenter
import kz.xbase.mstroy.states.HomeEditState
import kz.xbase.mstroy.views.HomeEditView

class HomeEditFragment : MviFragment<HomeEditView, HomeEditPresenter>(), HomeEditView {
    override fun createPresenter() = HomeEditPresenter (requireContext())

    override fun editIntent(): Observable<String> {
        TODO("Not yet implemented")
    }

    override fun render(state: HomeEditState) {
        TODO("Not yet implemented")
    }
}