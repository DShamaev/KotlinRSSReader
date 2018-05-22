package com.dmitriyshamaev.kotlinreader.network.xml

import okhttp3.ResponseBody
import com.tickaroo.tikxml.TikXml
import retrofit2.Converter
import java.io.IOException


class RSSTikXmlResponseBodyConverter<T>(private val tikXml: TikXml, private val clazz: Class<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): T {
        try {
            return tikXml.read(responseBody.source(), clazz)
        } finally {
            responseBody.close()
        }
    }
}