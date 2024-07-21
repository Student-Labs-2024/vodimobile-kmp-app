package com.vodimobile.utils.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized

private lateinit var dataStore: DataStore<Preferences>

private val lock = SynchronizedObject()

fun getDataStore(context: Any? = null): DataStore<Preferences> =
    synchronized(lock) {
        if (::dataStore.isInitialized) {
            dataStore
        } else {
//            PreferenceDataStoreFactory.createWithPath(produceFile = { producePath().toPath() })
            createDataStore(context = context)
                .also { dataStore = it }
        }
    }