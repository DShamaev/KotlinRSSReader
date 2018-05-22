package com.dmitriyshamaev.kotlinreader.network.pojo

import com.tickaroo.tikxml.annotation.*

@Xml
class RSSChannel {
    @PropertyElement
    var link: String? = null

    @Element(name="item")
    var itemList: List<Item>? = null


    @PropertyElement
    var title: String? = null
    @PropertyElement
    var language: String? = null
    @PropertyElement
    var ttl: Int = 0
    @PropertyElement
    var pubDate: String? = null

    override fun toString(): String {
        return "Channel{" +
                "link=" + link +
                ", itemList=" + itemList +
                ", title='" + title + '\''.toString() +
                ", language='" + language + '\''.toString() +
                ", ttl=" + ttl +
                ", pubDate='" + pubDate + '\''.toString() +
                '}'.toString()
    }

    @Xml
    class ItemImage {
        @Attribute(name = "href")
        var link: String? = null
    }

    @Xml
    class Item {

        @PropertyElement
        var title: String? = null//The title of the item.	Venice Film Festival Tries to Quit Sinking
        @PropertyElement
        var link: String? = null//The URL of the item.	http://www.nytimes.com/2002/09/07/movies/07FEST.html
        @PropertyElement
        var description: String? = null//The item synopsis.	Some of the most heated chatter at the Venice Film Festival this week was about the way that the arrival of the stars at the Palazzo del Cinema was being staged.
        @PropertyElement
        var author: String? = null//Email address of the author of the item. More.	oprah@oxygen.net
        @PropertyElement
        var pubDate: String? = null//	Indicates when the item was published. More.	Sun, 19 May 2002 15:21:36 GMT
        @PropertyElement
        var source: String? = null//	The RSS channel that the item came from. More.
        @Element(name = "itunes:image")
        var imageLink: ItemImage? = null//	The RSS channel that the item came from. More.

        override fun toString(): String {
            return "Item{" +
                    "title='" + title + '\''.toString() +
                    ", link='" + link + '\''.toString() +
                    ", description='" + description + '\''.toString() +
                    ", author='" + author + '\''.toString() +
                    ", pubDate='" + pubDate + '\''.toString() +
                    ", source='" + source + '\''.toString() +
                    '}'.toString()
        }
    }
}