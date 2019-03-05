package com.dk.newssync.data.mapper

import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.model.Article
import org.threeten.bp.OffsetDateTime
import java.util.*

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

fun List<Article>.toStories(q: String?, entryId: Long): List<Story> =
    this.map {
        Story(
            source = it.source?.name,
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            image = it.urlToImage,
            timestamp = timestamp(it.publishedAt),
            entryId = entryId
        )
    }

fun Story.timeAgo(): String {
    val time = timestamp ?: 0
    val calendar = Calendar.getInstance().apply {
        timeInMillis = time
    }

    val now = Calendar.getInstance().timeInMillis
    if (time > now || time <= 0) return "Just now"

    val diff = now - time
    return when {
        diff < MINUTE_MILLIS -> "Just now"
        diff < 2 * MINUTE_MILLIS -> "A minute ago"
        diff < 50 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
        diff < 90 * MINUTE_MILLIS -> "An hour ago"
        diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
        diff < 48 * HOUR_MILLIS -> "Yesterday at " + String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.HOUR_OF_DAY)) +
                ":" + String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.MINUTE))
        diff < 7 * DAY_MILLIS -> "${diff / DAY_MILLIS} days ago"
        else -> return String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.DAY_OF_MONTH)) + " " +
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " +
                calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)
    }

}

private fun timestamp(date: String?): Long {
    return OffsetDateTime.parse(date).toEpochSecond() * 1000
}
