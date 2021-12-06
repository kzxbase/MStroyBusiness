package kz.xbase.mstroy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*
import kz.xbase.mstroy.R

class CardsAdapter(val ctx:Context, val items : ArrayList<String>) : RecyclerView.Adapter<CardsAdapter.CardsViewHolder>() {
        var selected = 0
    class CardsViewHolder(val view:View) : RecyclerView.ViewHolder(view) {
        private val tvNumber =view.tv_number
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.itemView.tv_number.text = items[position]
        if(position==selected){
            holder.itemView.cv_back.setCardBackgroundColor(ctx.resources.getColor(R.color.white))
            holder.itemView.iv_icon.setImageResource(R.drawable.ic_credit_card_black)
            holder.itemView.tv_desc.setTextColor(ctx.resources.getColor(R.color.black))
            holder.itemView.tv_number.setTextColor(ctx.resources.getColor(R.color.black))
        }else{
            holder.itemView.cv_back.setCardBackgroundColor(ctx.resources.getColor(R.color.black))
            holder.itemView.iv_icon.setImageResource(R.drawable.ic_credit_card_white)
            holder.itemView.tv_desc.setTextColor(ctx.resources.getColor(R.color.white))
            holder.itemView.tv_number.setTextColor(ctx.resources.getColor(R.color.white))
        }
        holder.itemView.setOnClickListener {
            selected = position
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder =
        CardsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card,parent,false))
}