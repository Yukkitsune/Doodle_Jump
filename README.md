# Doodle Jump Clone for Android
![image](https://github.com/user-attachments/assets/86f8757d-041f-43d2-98c4-e1ad09999a21)

## 📌 Описание проекта
Этот проект представляет собой клон популярной игры **Doodle Jump**, разработанный на языке **Kotlin** с использованием **Jetpack Compose** и **Android SDK**. Игра адаптирована для мобильных устройств на базе **Android** и поддерживает управление с помощью акселерометра.

## 🎮 Основные возможности
- 🎨 **Графика и анимации** с использованием **Jetpack Compose**.
- 📱 **Поддержка сенсорного управления** с помощью акселерометра.
- 🏆 **Система рекордов** с сохранением лучших результатов.
- 🎵 **Звуковые эффекты** для улучшения игрового опыта.
- 🎭 **Выбор тем оформления** для персонализации игры.
- 🚀 **Бесконечный игровой процесс** с динамически генерируемыми платформами.

## 🛠️ Используемые технологии
- **Язык**: Kotlin
- **Среда разработки**: Android Studio
- **UI**: Jetpack Compose
- **Навигация**: Jetpack Navigation Component
- **Хранение данных**: SharedPreferences
- **Обработка движения**: Акселерометр

## 📂 Структура проекта
```
📦 DoodleJumpClone
 ┣ 📂 app
 ┃ ┣ 📂 src
 ┃ ┃ ┣ 📂 main
 ┃ ┃ ┃ ┣ 📂 kotlin/com/example/doodlejump
 ┃ ┃ ┃ ┃ ┣ 📜 MainActivity.kt
 ┃ ┃ ┃ ┃ ┣ 📜 GameViewModel.kt
 ┃ ┃ ┃ ┃ ┣ 📜 PlayerLogic.kt
 ┃ ┃ ┃ ┃ ┣ 📜 PlatformLogic.kt
 ┃ ┃ ┃ ┃ ┣ 📜 Score.kt
 ┃ ┃ ┃ ┃ ┣ 📜 Sound.kt
 ┃ ┃ ┃ ┣ 📂 res
 ┃ ┃ ┃ ┃ ┣ 📂 drawable (ресурсы графики)
 ┃ ┃ ┃ ┃ ┣ 📂 layout (разметка экранов)
 ┃ ┃ ┃ ┃ ┣ 📂 values (цвета, стили)
```

## 🚀 Установка и запуск
1. **Клонировать репозиторий**
   ```sh
   git clone https://github.com/yourusername/doodle-jump-clone.git
   ```
2. **Открыть проект в Android Studio**
3. **Запустить на эмуляторе или реальном устройстве**

## 📥 Входные данные
- **Настройки звука** (вкл/выкл)
- **Лучший результат** (сохраняется в SharedPreferences)

## 📤 Выходные данные
- **Текущий счет игрока**
- **Лучший результат**
- **Уведомления и подсказки** через Toast и UI-элементы

## 🛠 Разработка
### 🔹 Основные файлы
- `MainActivity.kt` – главная активность, управление жизненным циклом игры
- `GameViewModel.kt` – управление состоянием игры
- `PlayerLogic.kt` – обработка движения игрока
- `PlatformLogic.kt` – генерация и передвижение платформ
- `Score.kt` – подсчет очков
- `Sound.kt` – управление звуками

## 📌 Лицензия
Этот проект распространяется под лицензией **MIT**. Подробнее см. в файле [LICENSE](LICENSE).
## 🔥 Скриншоты
![image](https://github.com/user-attachments/assets/444a379b-c53d-494f-bead-31195841384a)
![image](https://github.com/user-attachments/assets/ab81d846-1679-485b-87ce-bbdc200d43a6)
![image](https://github.com/user-attachments/assets/3fab9201-4ec5-4a7d-a452-95c184b98b1c)
![image](https://github.com/user-attachments/assets/70045dde-4277-422a-80fc-33276fe3e146)
