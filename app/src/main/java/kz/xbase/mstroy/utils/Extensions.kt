package kz.xbase.mstroy.activity.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import kz.xbase.mstroy.R


fun String.showToast(
    context: Context?
) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
fun ImageView.setImage(url: String?) {
    Glide
        .with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}
fun ImageView.setLogo(url: String?){
    Glide
        .with(this.context)
        .load(url)
        .fitCenter()
        .into(this)
}



fun Fragment.add(
    fragmentManager: FragmentManager?, isAddToStack: Boolean?
) {
    val fragmentTransaction = fragmentManager?.beginTransaction()
    fragmentTransaction?.add(R.id.container, this, this.tag)
    fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    isAddToStack?.let {
        if (isAddToStack) {
            fragmentTransaction?.addToBackStack(tag)
        }
    }
    fragmentTransaction?.commit()
}

fun Fragment.replace(
    fragmentManager: FragmentManager?, isAddToStack: Boolean?
) {
    val fragmentTransaction = fragmentManager?.beginTransaction()
    fragmentTransaction?.replace(R.id.container, this, this.javaClass.name)
    fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    isAddToStack?.let {
        if (isAddToStack) {
            fragmentTransaction?.addToBackStack(this.javaClass.name)
        }
    }
    fragmentTransaction?.commit()
}

fun View.openKeyboard() {
    requestFocus()
    val inputMethodManager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, 0)
}

fun View.closeKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}


fun FragmentActivity.closeKeyboard() {
    currentFocus?.closeKeyboard()
}

fun FragmentActivity.openKeyboard() {
    currentFocus?.openKeyboard()
}