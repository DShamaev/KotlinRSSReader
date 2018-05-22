package com.dmitriyshamaev.kotlinreader.network.xml

import okhttp3.RequestBody
import com.tickaroo.tikxml.TikXml
import okhttp3.MediaType
import okio.Buffer
import retrofit2.Converter
import java.io.IOException


class RSSTikXmlRequestBodyConverter<T>(private val tikXml: TikXml) : Converter<T, RequestBody> {

    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        tikXml.write(buffer, value)
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }

    companion object {

        private val MEDIA_TYPE = MediaType.parse("application/xml; charset=UTF-8")
    }
}