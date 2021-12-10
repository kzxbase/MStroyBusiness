package kz.xbase.mstroy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.xbase.mstroy.R

class GoodsAdapter(val ctx:Context, val items : ArrayList<String>) : RecyclerView.Adapter<GoodsAdapter.GoodViewHolder>() {
    class GoodViewHolder(val view:View) : RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GoodViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodViewHolder =
        GoodViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_good,parent,false))
}