package com.dmitriyshamaev.kotlinreader.network.xml

import com.tickaroo.tikxml.TikXml
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class RSSTikXmlConverterFactory private constructor(private val tikXml: TikXml) : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out kotlin.Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        if (type !is Class<*>) {
            return null
        }
        val cls = type as Class<Any>

        return RSSTikXmlResponseBodyConverter(tikXml, cls)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out kotlin.Annotation>?, methodAnnotations: Array<out kotlin.Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        return RSSTikXmlRequestBodyConverter<Any>(tikXml)
    }

    init {
        if (tikXml == null) {
            throw NullPointerException("TikXml (passed as parameter) is null")
        }
    }

    companion object {

        @JvmOverloads
        fun create(tikXml: TikXml = TikXml.Builder().exceptionOnUnreadXml(false).build()): RSSTikXmlConverterFactory {
            return RSSTikXmlConverterFactory(tikXml)
        }
    }
}