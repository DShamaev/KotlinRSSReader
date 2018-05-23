package com.dmitriyshamaev.kotlinreader.util

import com.dmitriyshamaev.kotlinreader.model.NewsItem
import com.dmitriyshamaev.kotlinreader.network.pojo.RSS
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class POJOConverter {

    companion object {
        fun toNewsItem(rss: RSS): List<NewsItem> {
            val newsItems = mutableListOf<NewsItem> ()
            var items = rss.channel?.itemList
            items?.forEach { item ->
                newsItems.add(NewsItem(id = null,
                        title = item.title,
                        description = item.description,
                        pubDate = toUnixTimeStamp(item.pubDate!!),
                        imageURL = item.imageLink?.link,
                        link = item.link))
            }
            return newsItems.toList()
        }

        fun toUnixTimeStamp(dateStr: String): Long {
            return SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US).parse(dateStr).time
        }

        fun toDateString(unixTimestamp: Long): String {
            return DateFormat.getInstance().format(Date(unixTimestamp))
        }
    }
}