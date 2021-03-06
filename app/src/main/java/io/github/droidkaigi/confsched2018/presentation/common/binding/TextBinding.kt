package io.github.droidkaigi.confsched2018.presentation.common.binding

import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import io.github.droidkaigi.confsched2018.R
import io.github.droidkaigi.confsched2018.model.Date
import io.github.droidkaigi.confsched2018.model.toReadableDateTimeString
import io.github.droidkaigi.confsched2018.model.toReadableTimeString
import java.util.regex.Pattern

@BindingAdapter(value = ["bind:startDate", "bind:endDate"])
fun TextView.setPeriodText(startDate: Date?, endDate: Date?) {
    startDate ?: return
    endDate ?: return
    text = context.getString(
            R.string.time_period,
            startDate.toReadableTimeString(),
            endDate.toReadableTimeString()
    )
}

@BindingAdapter(value = ["bind:prefix", "bind:roomName"])
fun TextView.setRoomText(prefix: String?, roomName: String?) {
    prefix ?: return
    text = when (roomName) { null -> ""
        else -> context.getString(R.string.room_format, prefix, roomName)
    }
}

@BindingAdapter(value = ["android:text"])
fun TextView.setDateText(date: Date?) {
    date ?: return
    text = date.toReadableDateTimeString()
}

@BindingAdapter(value = ["bind:highlightText"])
fun TextView.setHighlightText(highlightText: String?) {
    // By toString, clear highlight text.
    val stringBuilder = SpannableStringBuilder(text.toString())
    if (TextUtils.isEmpty(highlightText)) {
        text = stringBuilder
        return
    }
    val pattern = Pattern.compile(highlightText, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(text)
    while (matcher.find()) {
        stringBuilder.setSpan(
                BackgroundColorSpan(ContextCompat.getColor(context, R.color.highlight_text)),
                matcher.start(),
                matcher.end(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        stringBuilder.setSpan(
                StyleSpan(Typeface.BOLD),
                matcher.start(),
                matcher.end(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
    text = stringBuilder
}
