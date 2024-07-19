@file:OptIn (ExperimentalForeignApi:: class)
package com.vodimobile.utils.data_store

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform. Foundation. NSFileManager
import platform.Foundation.NSUserDomainMask

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

actual fun createDataStore(context: Any?): DataStore<Preferences> {

    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    val value: String =
        requireNotNull(documentDirectory).path + "/${Constants.DATA_STORE_FILE_NAME}"

    val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            value.toPath()
        }
    )

    return dataStore
}