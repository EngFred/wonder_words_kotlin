package com.kotlin.wonderwords.features.details.utils

import android.content.Context
import android.content.Intent
import com.kotlin.wonderwords.features.details.domain.entity.QuoteDetails

fun shareQuote(context: Context, quote: QuoteDetails) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "\"${quote.body}\" - ${quote.author}")
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share quote via"))
}