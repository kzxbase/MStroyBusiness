package kz.xbase.mstroy.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_register_photo.view.*
import kz.xbase.mstroy.R

class RegisterPhotoAdapter(val ctx:Context,val items : ArrayList<Uri>) : RecyclerView.Adapter<RegisterPhotoAdapter.PhotoViewHolder>(){
    class PhotoViewHolder(val view:View) : RecyclerView.ViewHolder(view){
        private val image = view.iv_photo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_register_photo,parent,false))

    override fun onBindViewHolder(holder: PhotoViewHolder, @SuppressLint("RecyclerView") position: Int) {
            var photos=ctx.getExternalFilesDir("Pictures")
            if(photos!=null) {
                holder.itemView.iv_photo.setImageURI(items[position])
            }
        holder.itemView.setOnClickListener {
            val builder = AlertDialog.Builder(ctx)
            builder.setPositiveButton("Да",object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    items.remove(items[position])
                    notifyDataSetChanged()
                }
            })
            builder.setNegativeButton("Отмена",object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })
            builder.setTitle("Хотите удалить фото ?")
            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}