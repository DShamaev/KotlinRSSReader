package com.dmitriyshamaev.kotlinreader.network.pojo

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml
class RSS {

    @Attribute
    var version: String? = null

    @Element(name="channel")
    var channel: RSSChannel? = null

    override fun toString(): String {
        return "RSS{" +
                "version='" + version + '\''.toString() +
                ", channel=" + channel +
                '}'.toString()
    }
}