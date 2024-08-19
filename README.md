![Логотип компании "Водимобиль"](https://github.com/Student-Labs-2024/vodimobile-kmp-app/blob/git-flow/repository-docs/androidApp/src/main/res/drawable/logoapp.png?raw=true)

![Язык программирования Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white) ![Язык программирования Swift](https://img.shields.io/badge/swift-F54A2A?style=for-the-badge&logo=swift&logoColor=white) ![Инструмент Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) ![Android Studio](https://img.shields.io/badge/android%20studio-346ac1?style=for-the-badge&logo=android%20studio&logoColor=white) ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)

![iOS](https://img.shields.io/badge/iOS-000000?style=for-the-badge&logo=ios&logoColor=white) ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)

## Суть проекта 💫
Автоматизировать процесс аренды автомобилей в Омске путём создания Android и IOS приложений. Приложения интегрированы с CRM «WS автопрокат», которую использует «Водимобиль».

## Содержание 📖
* [Как начать](#start)
  + [Git hub](#git-hub)
  + [Как склонировать проект](#how)
* [Где работать](#where)
* [Начальные действия](#actions)
* [Сборка](#build)
  + [Собрать для ОС Android](#build-a)
  + [Собрать для IOS](#build-ios)
* [Структура проекта](#project)
* [Технологический стек](#tech)
* [Git flow](#git-flow-)
* [Диаграмма C4](#diagramm)

<a name="start"><h2>Как начать 🐣</h2></a>
Для начала стоит склонировать репозиторий себе на компьютер путём выполнения команды Git Hub.

### Git hub
_Если у вас нет Git Hub на устройстве, то установите его по [ссылке](https://git-scm.com/downloads)._

<a name="how"><h2>Как склонировать проект</h2></a>
Путём почти одновременного начатия клавишь `Win` + `R` (на ОС Windows) откроется окно, в котором нужно вписать `cmd`. В открывшемся окошке перейдите в ту директорию на вашем устройстве, куда хотите склонировать репозирторий с проектом, для этого используйте `cd Путь к папке`. После наберите команду `git clone https://github.com/Student-Labs-2024/vodimobile-kmp-app.git`. Дождитесь выполнения команды. После чего у вас по указанному пути появится папка с проектом.

<a name="where"><h2>Где работать 🤖</h2></a>
Для работы вам понадобится Android Studio. Её можно скачать на [официальном сайте](https://developer.android.com/studio?hl=ru).
После загрузки откройте склонированный с Git Hub проект. Дождитесь загрузки проекта.

<a name="actions"><h2>Начальные действия ❗</h2></a>
Перед началом работы с проектом (сборке его исполняемых файлов или внесение изменений в код) добавьте в `local.properties` сделующие строчки:
```
crm.server =
crm.port =
crm.login =
crm.password.hash =
crm.test.access.token = 
crm.test.refresh.token = 
```
После знака равно запишите значения сервера и порта «Водимобиль» без пробелов.

<a name="build"><h2>Сборка 🛠️</h2></a>
Откройте терминал внутри Android Studio. Если Вы не можете найти терминала нет, то воспользуйтесь такой последовательностью для навигации внутри Android Studio к терминалу _View > Tool Windows > Terminal_. Вы можете собрать исполняемые файлы для ОС Android или IOS.

<a name="build-a"><h3>Собрать для ОС Android</h3></a>
Для того, чтобы собрать исполняемый файл для ОС Android (APK) впишите в терминале следующую команду ```./gradlew assembleDebug```. Дождитель выполнения. При успешном выполнении, Вы увидите надпись "Build Successfull". Далее Вы сможете найти APK в корне проекта по следующему пути `app/build/outputs/apk/app-debug.apk`.

<a name="build-ios"><h3>Собрать для IOS</h3></a>
_Будет позже_

<a name="project"><h2>Структура проекта 📋</h2></a>
Проект сделан с использованием технологии KMP - _Kotlin Multiplatform_. В архитектуре используется три модуля AndroidApp, IosApp, Shared.

- AndroidApp - модуль, содержащий приложение для ОС Android.
- IosApp - модуль, содержащий приложение для IOS.
- Shared - общий модуль, используемый обоями платформами.

Изображение, описывающее архитектуру проекта

![KMP flow](https://github.com/Student-Labs-2024/vodimobile-kmp-app/blob/git-flow/repository-docs/docs/images/%D0%B2%D0%BE%D0%B4%D0%B8%D0%BC%D0%BE%D0%B1%D0%B8%D0%BB%D1%8C-KMP%20flow.png?raw=true)

<a name="tech"><h2>Технологический стек</h2></a>
**Android 👽**
- [Jetpack Compose](https://developer.android.com/develop/ui/compose/tutorial)
- Clean architecture
- Минимальная поддерживаемая версия Android SDK 21, целевая - 34
- Паттерн MVI
- [Koin](https://insert-koin.io/docs/quickstart/android/)
- [Slack lint](https://github.com/slackhq/compose-lints)
  
**IOS 🍏**
- [SwiftUI](https://developer.apple.com/tutorials/swiftui/)
- Паттерн MVVM
- Clean architecture
- Минимальная поддерживаемая версия IOS 16, максимальная - текущая.
- [SwiftLint](https://github.com/realm/SwiftLint)
  
**Shared 🔮**
- [Data Store](https://developer.android.com/kotlin/multiplatform/datastore) с использованием [Mutex](https://github.com/android/kotlin-multiplatform-samples/blob/main/DiceRoller/shared/src/commonMain/kotlin/com/google/samples/apps/diceroller/createDataStore.kt) в KMP
- [Ktor](https://ktor.io/)
- [SQLDelight](https://github.com/cashapp/sqldelight)
- [Koin KMP](https://insert-koin.io/docs/reference/koin-mp/kmp/)
- [Coroutines](https://kotlinlang.org/docs/multiplatform-add-dependencies.html#kotlinx-libraries)
- [Skie](https://skie.touchlab.co/configuration/)
- [Buildkonfig](https://github.com/yshrsmz/BuildKonfig)
- [Date time](https://github.com/Kotlin/kotlinx-datetime)

## Git flow 📀
**Коммиты**
Все коммиты назваются на английском языке. Описание для коммата также пишется на английском языке. Пример формления коммита представлен ниже.
Структура коммита должна быть следующая: <br>
<type>[optional scope]: <description> <br>
[optional body] <br>
[optional footer(s)] <br>

_Пример:_  <br>
feat: Create auto cards into main screen <br>
<p>
[+] Create auto card list into main screen <br>
[+] Adding auto card component and use it <br>
[+] Adding custom resources into project <br>
[+] Adding view model and model for popular auto list <br>
[-] ... <br>
[~] ... <br>
</p>
Обозначения списка изменения в описании коммита: <br>
- [+] - добавление новой фичи (функционала); <br>
- [-] - удаление функционала; <br>
- [~] - исправвление функционала. <br>


_Почитать про Git flow можно [здесь](https://www.conventionalcommits.org/en/v1.0.0/)._

**Ветки**<br>
Ветки называем по следующему шаблону `platform/action/name`.<br>
`platfotm` - android-app, ios-app, kmp-module, git-flow;<br>
`action` - hot-fix или feature и т.п. (иногда, может не быть);<br>
`name` - название вашей фичи, например “profile-screen”.<br>

Все ветки для разработки создаются от `develop`.<br>

**Пул реквесты**<br>
Описание для пул реквеста и его название оформляется на английском языке. Присваиваете пул реквесту себя, в качестве разработчика, ставите ревьювера, присваиваете таг (label). Разговор в пул рекветсе можно вести на русском языке. <br>
Ниже представлен шаблон и пример
<br>
### Заголовок пул реквеста: Основная фича

### Changes:

1. **change1**:
   - definition1
   - definition2

2. **change1**:
   - definition1


Пример:
### Changes:

1. **New UI view**:
   - Created the detail modal view with auto data
   - Added the modal auto view into main screen auto list

2. **Custom resources for UI**:
   - New custom images and localizable strings


**Темы**<br>
Название темы и её описание пишется на русском языке.

**Релиз**<br>
После завершения определённого этапа разработки, все изменения переходят в `master` ветку. После чего следует создать релиз в Git Hub.<br>

<a name="diagramm"><h2>Диаграмма C4 📊</h2></a>

**System context**

![System context](https://github.com/Student-Labs-2024/vodimobile-kmp-app/blob/git-flow/repository-docs/docs/images/%D0%94%D0%B8%D0%B0%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B0%20%D0%A14%20%D0%92%D0%BE%D0%B4%D0%B8%D0%BC%D0%BE%D0%B1%D0%B8%D0%BB%D1%8C-System%20Context.png?raw=true)


**Containers context**

![Containers context](https://github.com/user-attachments/assets/7f8e6e52-d4db-4cac-8625-7e14b16a8591?raw=true)


**Components context KMP**

![Components context KMP](https://github.com/Student-Labs-2024/vodimobile-kmp-app/blob/git-flow/repository-docs/docs/images/%D0%94%D0%B8%D0%B0%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B0%20%D0%A14%20%D0%92%D0%BE%D0%B4%D0%B8%D0%BC%D0%BE%D0%B1%D0%B8%D0%BB%D1%8C-Components%20Context%20KMP%20app.png?raw=true)


**Components context IOS**

![Components context IOS](https://github.com/user-attachments/assets/9d540749-caff-4c74-890a-9096336ea7c2?raw=true)


**Components context Android**

![Components context Android](https://github.com/Student-Labs-2024/vodimobile-kmp-app/blob/git-flow/repository-docs/docs/images/%D0%94%D0%B8%D0%B0%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B0%20%D0%A14%20%D0%92%D0%BE%D0%B4%D0%B8%D0%BC%D0%BE%D0%B1%D0%B8%D0%BB%D1%8C-Components%20Context%20Android%20app.png?raw=true)
