package com.kotlin.wonderwords.core.utils

import android.content.Context

import android.widget.Toast

fun showToast(
    context: Context,
    text: String = "The form is invalid!",
    toastLength: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(context, text, toastLength).show()
}