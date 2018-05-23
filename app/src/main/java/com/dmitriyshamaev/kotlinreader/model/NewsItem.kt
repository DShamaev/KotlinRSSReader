package com.dmitriyshamaev.kotlinreader.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.dmitriyshamaev.kotlinreader.util.POJOConverter

@Entity(tableName = "newsItem")
data class NewsItem(@PrimaryKey(autoGenerate = true) var id: Long?,
                    @ColumnInfo(name = "title") var title: String?,
                    @ColumnInfo(name = "description") var description: String?,
                    @ColumnInfo(name = "pubDate") var pubDate: Long?,
                    @ColumnInfo(name = "link") var link: String?,
                    @ColumnInfo(name = "imageURL") var imageURL: String?
                      ) {
    val pubDateString: String
    get() {
        return POJOConverter.toDateString(pubDate!!)
    }
}

