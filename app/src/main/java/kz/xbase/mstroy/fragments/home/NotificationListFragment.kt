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
import kotlinx.android.synthetic.main.fragment_notification_list.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.showMessage
import kz.xbase.mstroy.adapters.NotificationsAdapter
import kz.xbase.mstroy.presenters.NotificationListPresenter
import kz.xbase.mstroy.states.NotificationListState
import kz.xbase.mstroy.views.NotificationListView

class NotificationListFragment : MviFragment<NotificationListView,NotificationListPresenter>(),NotificationListView {
    private lateinit var loadTrigger : PublishSubject<Int>
    private var notificationsList :ArrayList<String> = arrayListOf()
    private var isOrder = true
    private val notificationsAdapter by lazy { NotificationsAdapter(requireContext(),isOrder,notificationsList) }
    var isFirstRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadTrigger = PublishSubject.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            isOrder =it.getBoolean("isOrder")
        }
        return inflater.inflate(R.layout.fragment_notification_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_notifications.apply {
            adapter = notificationsAdapter
            layoutManager =LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        }
        notificationsList.add("Сушкилер")
        notificationsList.add("хуеттап")
        notificationsList.add("кеткен")
        notificationsList.add("сиякты")
        notificationsAdapter.notifyDataSetChanged()
        tv_title.text = if (isOrder){"Новые заказы"}else{"Акций"}
        setListeners()
    }
    private fun setListeners(){
        cv_back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun createPresenter() = NotificationListPresenter(requireContext())

    override fun getNotiIntent() = loadTrigger

    override fun render(state: NotificationListState) {
        when(state){
            is NotificationListState.MainState -> {
                progress.visibility = View.GONE
                ll_main.visibility = View.VISIBLE
            }
            is NotificationListState.ShowErrorMessage -> {
                progress.visibility = View.GONE
                ll_main.visibility = View.VISIBLE
                ll_main.showMessage(state.error)
            }
            is NotificationListState.Loading -> {
                progress.visibility = View.VISIBLE
                ll_main.visibility = View.GONE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(isOrder:Boolean) = NotificationListFragment().apply {
            arguments = Bundle().apply {
                putBoolean("isOrder",isOrder)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(isFirstRun){
            loadTrigger.onNext(1)
            isFirstRun = false
        }
    }

}