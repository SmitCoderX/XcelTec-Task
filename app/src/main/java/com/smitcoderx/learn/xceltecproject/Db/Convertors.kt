package com.smitcoderx.learn.xceltecproject.Db

import androidx.room.TypeConverter
import com.smitcoderx.learn.xceltecproject.model.Source

class Convertors {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }

}