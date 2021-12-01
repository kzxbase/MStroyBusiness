package kz.xbase.mstroy.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.fragment_home.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.setImage
import java.util.*

class HomeFragment : Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_photo.setImage("https://www.radidomapro.ru/images/unique/585-640-20180219_110549_585-640-20170515131741lerua.jpg")
        val dayCards = listOf<MaterialCardView>(cv7,cv1,cv2,cv3,cv4,cv5,cv6)
        val tvDayCards = listOf<TextView>(tv7,tv1,tv2,tv3,tv4,tv5,tv6)
        val pointerDayCards = listOf<MaterialCardView>(cv_7,cv_1,cv_2,cv_3,cv_4,cv_5,cv_6)
        val calendar = Calendar.getInstance()
        dayCards[calendar[Calendar.DAY_OF_WEEK]-1].setCardBackgroundColor(resources.getColor(R.color.white))
        tvDayCards[calendar[Calendar.DAY_OF_WEEK]-1].setTextColor(resources.getColor(R.color.black))
        pointerDayCards[calendar[Calendar.DAY_OF_WEEK]-1].setCardBackgroundColor(resources.getColor(R.color.white))
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}