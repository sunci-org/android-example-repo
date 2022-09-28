package com.example.finalapplication.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.finalapplication.R
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DateFormat
import java.util.*

fun CircleImageView.loadImageByUrl(url: String?, context: Context?) {
    if (context != null) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_account_circle_24)
            .error(R.drawable.ic_account_circle_24)
            .into(this)
    }
}

fun String.showMessage(context: Context?){
    if(this.isNullOrEmpty()) return
    Toast.makeText(context, this,Toast.LENGTH_SHORT).show()
}

fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}

fun ImageView.loadImageByUrl(url: String?, context: Context?) {
    if (context != null) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_account_circle_24)
            .error(R.drawable.ic_account_circle_24)
            .into(this)
    }
}
