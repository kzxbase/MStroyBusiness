package kz.xbase.mstroy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.xbase.mstroy.R

class PersonalAdapter(val ctx:Context, val items : ArrayList<String>) : RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder>() {
    var onItemClick: (() -> Unit) = {}
    class PersonalViewHolder(val view:View) : RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PersonalViewHolder, position: Int) {
        holder.view.setOnClickListener {
            onItemClick.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalViewHolder =
        PersonalViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_personal,parent,false))

}