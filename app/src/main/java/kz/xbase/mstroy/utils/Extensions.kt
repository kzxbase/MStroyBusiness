package kz.xbase.mstroy.activity.utils

import android.content.Context
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kz.xbase.mstroy.R
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*


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
fun View.showMessage(message:String){
    Snackbar.make(this,message,Snackbar.LENGTH_LONG).show()
}
fun Fragment.showToast(message: String){
    Toast.makeText(this.context,message,Toast.LENGTH_SHORT).show()
}


fun FragmentActivity.closeKeyboard() {
    currentFocus?.closeKeyboard()
}

fun FragmentActivity.openKeyboard() {
    currentFocus?.openKeyboard()
}
fun View.inFromRightAnimation() {
    val inFromRight: Animation = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, +1.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f
    )
    inFromRight.duration = 500
    inFromRight.interpolator = AccelerateInterpolator()
    this.startAnimation(inFromRight)
}
fun View.inFromLeftAnimation() {
    val inFromRight: Animation = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, -1.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f
    )
    inFromRight.duration = 500
    inFromRight.interpolator = AccelerateInterpolator()
    this.startAnimation(inFromRight)
}
fun View.outToLeftAnimation(){
    val inFromRight: Animation = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, -1.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f
    )
    inFromRight.duration = 500
    inFromRight.interpolator = AccelerateInterpolator()
    this.startAnimation(inFromRight)
}
fun View.outToRightAnimation(){
    val inFromRight: Animation = TranslateAnimation(
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 1.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f
    )
    inFromRight.duration = 500
    inFromRight.interpolator = AccelerateInterpolator()
    this.startAnimation(inFromRight)
}
fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}