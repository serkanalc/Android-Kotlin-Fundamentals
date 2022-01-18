package com.begumyolcu.devbytesrepository.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.begumyolcu.devbytesrepository.domain.DevByteVideo

/**
 * Database entityleri bu dosyada olmalıdır. Bunlar veritabanından okuma ve
 * yazmadan sorumludur.
 */

/**
 * DatabaseVideo veritabaınındaki bir video entity'sini temsil eder.
 */
@Entity
data class DatabaseVideo constructor(
    @PrimaryKey
    val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String)


/**
 * DatabaseVideolarını domain entitylerine mapleyin.
 */
fun List<DatabaseVideo>.asDomainModel(): List<DevByteVideo> {
    return map {
        DevByteVideo(
            url = it.url,
            title = it.title,
            description = it.description,
            updated = it.updated,
            thumbnail = it.thumbnail)
    }
}