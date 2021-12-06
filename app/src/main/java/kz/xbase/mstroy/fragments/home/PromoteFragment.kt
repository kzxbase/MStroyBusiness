package kz.xbase.mstroy.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_promote.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.HomeActivity
import okio.JvmStatic

class PromoteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_promote,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cv_back.setOnClickListener {
            (activity as HomeActivity).navigateHomeFragment()
        }
    }
    companion object{
        @JvmStatic
        fun newInstance() = PromoteFragment()
    }
}