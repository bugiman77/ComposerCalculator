package com.bugiman.data.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.bugiman.data.proto.SettingsProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SettingsProtoSerializer : Serializer<SettingsProto> {

    override val defaultValue: SettingsProto = SettingsProto.getDefaultInstance()

    // Чтение из файла
    override suspend fun readFrom(input: InputStream): SettingsProto {
        try {
            return SettingsProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    // Запись в файл
    override suspend fun writeTo(t: SettingsProto, output: OutputStream) {
        t.writeTo(output)
    }

}