package com.mcwindy.ddrhelper.model

import com.squareup.moshi.*
import java.lang.reflect.Type

class ResourceAdapter : JsonAdapter.Factory {
    private val type = Types.newParameterizedType(Resource::class.java, DdnetRankData::class.java)

    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        return if (type == this.type) {
            ResourceJsonAdapter(moshi)
        } else null
    }

    private class ResourceJsonAdapter(moshi: Moshi) : JsonAdapter<Resource<DdnetRankData>>() {
        private val ddnetRankDataAdapter: JsonAdapter<DdnetRankData> = moshi.adapter(DdnetRankData::class.java)

        @ToJson
        override fun toJson(writer: JsonWriter, value: Resource<DdnetRankData>?) {
            writer.beginObject()
            when (value) {
                is Resource.Success -> {
                    writer.name("status").value("success")
                    writer.name("data")
                    ddnetRankDataAdapter.toJson(writer, value.data)
                }
                is Resource.Error -> {
                    writer.name("status").value("error")
                    writer.name("message").value(value.message)
                }
                else -> {
                    writer.name("status").value("unknown")
                }
            }
            writer.endObject()
        }

        @FromJson
        override fun fromJson(reader: JsonReader): Resource<DdnetRankData>? {
            var status: String? = null
            var data: DdnetRankData? = null
            var message: String? = null

            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "status" -> status = reader.nextString()
                    "data" -> data = ddnetRankDataAdapter.fromJson(reader)
                    "message" -> message = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return when (status) {
                "success" -> if (data != null) Resource.Success(data) else Resource.Error("Data is null")
                "error" -> Resource.Error(message ?: "Unknown error")
                else -> Resource.Error("Unknown status")
            }
        }
    }
}