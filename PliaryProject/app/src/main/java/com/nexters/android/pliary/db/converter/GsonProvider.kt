package com.nexters.android.pliary.db.converter

import com.google.gson.*
import org.threeten.bp.ZonedDateTime
import java.lang.reflect.Type


internal object GsonProvider {
    val gson = GsonBuilder()
        .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeJsonConverter())
        .create()
}
internal class ZonedDateTimeJsonConverter : JsonSerializer<ZonedDateTime>,
    JsonDeserializer<ZonedDateTime> {

    private val converter = ZonedDateTimeConverter()

    override fun serialize(src: ZonedDateTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(converter.fromDate(src))
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ZonedDateTime {
        return converter.toDate(json.asString)
    }
}