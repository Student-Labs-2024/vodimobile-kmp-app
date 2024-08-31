## Структура remote
В этой директории находятся модели данных, используемые для взаимодействия с удаленными сервисами.

| Директория | Назначение |
|------------|---------|
|dto| Содержит Data Transfer Object (DTO) классы, которые используются для представления данных, передаваемых между клиентом и сервером. |
|either| Содержит Sealed-класс, представляющий возможные результаты выполнения операций с CRM-сервисом |


### DTO-классы:

__Bid__
- BidCostDTO: Содержит данные о стоимости заказа, включая результат, стоимость, предоплату, депозит и сообщение об ошибке.
- BidCostParams: Содержит параметры для запроса расчета стоимости заказа, включая идентификатор автомобиля, начало и конец аренды, идентификаторы начального и конечного пунктов, а также список дополнительных услуг.
- BidStatusDTO: Содержит данные о статусе заказа, включая результат, идентификатор заказа, название статуса и заголовок статуса.
- BidCreateDTO: Содержит данные о созданном заказе, включая результат, идентификатор заказа, номер заказа и сообщение об ошибке.

__Car__
- CarFreeListDTO: Содержит данные о списке свободных автомобилей, включая результат и массив идентификаторов автомобилей.
- CarDTO: Содержит данные об одном автомобиле, включая идентификатор, модель, номер, идентификатор города, год и GUID фотографии.
- CarListDTO: Содержит данные о списке автомобилей, включая результат и массив CarDTO.

__Place__
- PlaceDTO: Содержит данные о списке пунктов, включая результат и массив PlacesDTO.
- PlacesDTO: Содержит данные об одном пункте, включая идентификатор, название, идентификатор города, стоимость доставки и признак архива.

__Service__
- ServiceDTO: Содержит данные о списке дополнительных услуг, включая результат и массив ServicesDTO.

__Tariff__
-  TariffDTO: Содержит данные об одном тарифе, включая минимальное и максимальное количество дней аренды, а также стоимость.
-  TariffListDTO: Содержит данные о списке тарифов, включая результат и массив CarTariffDTO. Также есть вспомогательные методы для преобразования данных в список тарифов.
-  CarTariffDTO: Содержит данные о тарифах для одного автомобиля, включая идентификатор автомобиля, депозит и список TariffDTO.

__Token__
- RefreshTokenRequest: Содержит данные для запроса обновления токена доступа, включая токен.

__User__
-  UserRequest: Содержит данные для запроса авторизации пользователя, включая имя пользователя, хэш пароля и флаг длительного токена.
-  UserResponse: Содержит данные об ответе на запрос авторизации, включая токен доступа, токен обновления и срок действия.

###  either:
- CrmError:  представляет ошибку, возникшую при выполнении операции. Содержит статус ошибки.
- CrmLoading:  представляет состояние загрузки, когда операция находится в процессе выполнения.
- CrmData:  представляет успешный результат операции, содержащий данные.