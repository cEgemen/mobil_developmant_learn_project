package com.lq.countrys.utility

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lq.countrys.R


fun ImageView.getImage(url : String,context:Context)
{
    val options = RequestOptions().placeholder(drawableProgress(context)).error(com.google.android.material.R.drawable.mtrl_ic_error)
       Glide.with(context)
           .setDefaultRequestOptions(options)
           .load(url)
           .into(this)
           .onStart();

}

fun drawableProgress (context :Context) : CircularProgressDrawable {
     return CircularProgressDrawable(context).apply {
          strokeWidth = 8f;
          centerRadius = 40f
//         start()
     }



}