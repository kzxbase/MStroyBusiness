package kz.xbase.mstroy.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_register_photo.view.*
import kz.xbase.mstroy.R

class RegisterPhotoAdapter(val ctx:Context,val items : ArrayList<String>) : RecyclerView.Adapter<RegisterPhotoAdapter.PhotoViewHolder>(){
    class PhotoViewHolder(val view:View) : RecyclerView.ViewHolder(view){
        private val image = view.iv_photo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_register_photo,parent,false))

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if(items[position].isEmpty()){
            holder.itemView.iv_photo.setImageResource(R.drawable.ic_camera)
        }else{
            var photos=ctx.getExternalFilesDir("Pictures")
            if(photos!=null) {
                holder.itemView.iv_photo.setImageURI(Uri.parse(photos.absolutePath+"/"+items[position]))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}