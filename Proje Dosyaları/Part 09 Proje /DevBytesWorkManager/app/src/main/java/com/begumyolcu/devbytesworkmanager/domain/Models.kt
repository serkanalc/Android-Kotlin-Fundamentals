package com.begumyolcu.devbytesworkmanager.domain

import com.begumyolcu.devbytesworkmanager.util.smartTruncate

/**
 *  Domain nesneleri, uygulamamızdaki şeyleri temsil eden düz Kotlin data classlarıdır.
 * Bunlar, ekranda görüntülenmesi veya uygulama tarafından manipüle edilmesi gereken nesnelerdir.
 *
 * @see database -> veritabanına eşlenen nesneler için database'e bakın
 * @see network -> ağ çağrılarını parse eden veya hazırlayan nesneler için network'e bakın
 */

/**
 * Videolar, oynatılabilen bir devbyte'ı temsil eder.
 */
data class DevByteVideo(val title: String,
                        val description: String,
                        val url: String,
                        val updated: String,
                        val thumbnail: String) {
    /**
     * Short description UI'da kesilmiş açıklamaları görüntülemek için kullanılır
     */
    val shortDescription: String
        get() = description.smartTruncate(200)
}