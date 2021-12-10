package kz.xbase.mstroy.fragments.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_analytics_good.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.adapters.GoodsAdapter

class AnalyticsGoodFragment : Fragment() {
    private var goodList:ArrayList<String> = arrayListOf()
    private val goodAdapter by lazy { GoodsAdapter(requireContext(),goodList) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_analytics_good,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_good.apply {
            adapter = goodAdapter
            layoutManager =LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        }
        goodList.add("")
        goodList.add("")
        goodList.add("")
        goodList.add("")
        goodList.add("")
        goodList.add("")
    }
    companion object{
        @JvmStatic
        fun newInstance() = AnalyticsGoodFragment()
    }
}