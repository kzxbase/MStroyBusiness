package kz.xbase.mstroy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_notification.view.*
import kz.xbase.mstroy.R

class NotificationsAdapter(val ctx:Context,val isOrder:Boolean,val items : ArrayList<String>) : RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(val view:View) : RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.itemView.tv_title.text = "Название уведомления"
        holder.itemView.tv_desc.text = items[position]
        if (isOrder){
            holder.itemView.iv_icon.setImageResource(R.drawable.ic_shopping_cart)
        }else{
            holder.itemView.iv_icon.setImageResource(R.drawable.ic_discount_noti)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder =
        NotificationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_notification,parent,false))
}