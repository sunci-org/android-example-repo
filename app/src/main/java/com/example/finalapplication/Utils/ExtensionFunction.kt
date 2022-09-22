package com.example.finalapplication.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.example.finalapplication.R
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadImageByUrl(url: String?, context: Context?) {
    if (context != null) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_account_circle_24)
            .error(R.drawable.ic_account_circle_24)
            .into(this)
    }
}
