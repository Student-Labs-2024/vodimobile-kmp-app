package com.vodimobile.utils.data_store

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.vodimobile.MainActivity
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CreateDataStore_androidKtTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context
    private lateinit var dispatcher: TestDispatcher


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() = runTest {
        context = rule.activity.applicationContext
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
    }

    @Test
    fun createDataStore() {
        val dataStore: DataStore<Preferences> = createDataStore(context = context)
        assertNotNull(dataStore)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun editDataStoreData() = runTest {
        rule.setContent {
            VodimobileTheme {
                val dataStore: DataStore<Preferences> =
                    remember { createDataStore(context = context) }

                LaunchedEffect(key1 = Unit) {
                    // 1. Запускаем редактирование в отдельном сопрограмме
                    launch(dispatcher) {
                        dataStore.edit { preferences ->
                            preferences[TestTags.TestDataStore.PREFERENCES_KEY_TEST] =
                                TestTags.TestDataStore.CreateDataStore_androidKtTest.mock_editDataStoreData
                        }
                    }

                    // 2. Ждем, пока редактирование завершится
                    advanceUntilIdle()

                    // 3. Получаем значение из flow
                    val stringFlow: Flow<String> = dataStore.data.map { preferences ->
                        preferences[TestTags.TestDataStore.PREFERENCES_KEY_TEST] ?: ""
                    }

                    // 4. Собираем данные из flow и ждем, пока значение будет получено
                    stringFlow.collect { value ->
                        assertEquals(
                            expected = TestTags.TestDataStore.CreateDataStore_androidKtTest.expect_editDataStoreData,
                            actual = value,
                            message = TestTags.TestDataStore.CreateDataStore_androidKtTest.msg_editDataStoreData
                        )
                        // Важно: завершаем collect, чтобы тест не завис
                        advanceUntilIdle()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getDataStoreUsingMutexTest() = runTest {
        val dataStore: DataStore<Preferences> = getDataStore(context = context)

        launch {
            dataStore.edit { preferences ->
                preferences[TestTags.TestDataStore.PREFERENCES_KEY_TEST] =
                    TestTags.TestDataStore.CreateDataStore_androidKtTest.mock_editDataStoreData
            }
        }

        advanceUntilIdle()

        val stringFlow: Flow<String> = dataStore.data.map { preferences ->
            preferences[TestTags.TestDataStore.PREFERENCES_KEY_TEST] ?: ""
        }

        stringFlow.collect { value ->
            assertEquals(
                expected = TestTags.TestDataStore.CreateDataStore_androidKtTest.expect_editDataStoreData,
                actual = value,
                message = TestTags.TestDataStore.CreateDataStore_androidKtTest.msg_editDataStoreData
            )
            advanceUntilIdle()
        }
    }
}