## Data_store

Представляет реализацию интерфейса [UserDataStoreRepository].
* Он используется для управления пользовательскими данными, хранящимися в [DataStore].

### UserDataStoreRepositoryImpl
| Функция | Назначение |
|---------|-----------|
| editUserData| Обновляет пользовательские данные в [DataStore]. |
| getUserData | Получает пользовательские данные из [DataStore] в виде потока [Flow] |
| editPreregister | Обновляет предварительно зарегистрированные данные пользователя в [DataStore] |
| editPassword | Обновляет пароль пользователя в [DataStore] |
