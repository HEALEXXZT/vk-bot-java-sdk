# VK-BOT-JAVA-SDK


<img src="https://i.imgur.com/XvMSJa2.png"></img>
###### Удобная и простая библиотека, помогающая легко и быстро создать бота для ВКонтакте

---

С помощью данной библиотеки можно довольно просто взаимодействовать с [VK API](https://vk.com/dev/manuals) для создания ботов и не только. 
Функционал прекрасно подходит как для сообществ, так и для личных страниц.

Официальная версия на github: https://github.com/petersamokhin/vk-bot-java-sdk

Официальная версия: [![Maven Central](https://img.shields.io/maven-central/v/com.petersamokhin/vk-bot-java-sdk.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.petersamokhin%22%20AND%20a:%22vk-bot-java-sdk%22)

Переписанная версия: [![Maven Central](https://img.shields.io/maven-central/v/com.github.healexxzt/vk-bot-keyboard.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.healexxzt%22%20AND%20a:%22vk-bot-keyboard%22)

## Пример

![Example](https://pp.userapi.com/c836720/v836720502/5f450/LLXsepZc9EE.jpg)

Реализуем возможность получать уведомления только о сообщениях нужного типа:
```java
Group group = new Group(151083290, "access_token");
    
group.onSimpleTextMessage(message ->
     new Message()
         .from(group)
         .to(message.authorId())
         .text("Что-то скучновато буковки читать. Картинку кинь лучше.")
         .send()
);

group.onPhotoMessage(message ->
    new Message()
         .from(group)
         .to(message.authorId())
         .text("Уже лучше. Но я тоже так могу. Что дальше?")
         .photo("/Users/PeterSamokhin/Desktop/topoviy_mem.png")
         .send()
);

group.onVoiceMessage(message ->
    new Message()
         .from(group)
         .to(message.authorId())
         .text("Не охота мне голосовые твои слушать.")
         .doc("https://vk.com/doc62802565_447117479")
         .send()
);
```
## Функционал: версия 0.1.3 (25.08.2017)
* Работа с личными сообщениями сообществ и личных страниц — необходим только [access_token](https://vk.com/dev/access_token).
* Возможность обработки сообщений только нужного типа (голосовые, простые текстовые, со стикером, и так далее)
* Можно, также, реагировать не только на сообщения с определенными вложениями, но и на сообщения, содержащие только определенные команды:
```java
// Самый простой вариант для одной команды
group.onCommand("/start", message -> 
    // do something with message
);

// Если команд много
group.onCommand(new String[]{"/start", "/bot", "hello"}, message ->
    // do something with message
);
```
* При прикреплении фотографий и документов к сообщению используется новый метод API, позволяющий загружать вложения напрямую в диалог. Таким образом все вложения отправляются как бы от имени пользователя, и никаких лимитов нет.
* В случае загрузки фото, документа, обложки и прочего по ссылке, файл не будет скачан, а напрямую, как массив байт, будет передан и загружен в VK. Благодаря этому достигнута высокая скорость обработки сообщений.
* Возможность реагировать на то, что пользователь начал печатать и автоматически начинать печатать всем пользователям, от которых пришло сообщение (статус ___...набирает сообщение...___ будет показан в течение 10 секунд, либо пока вы не отправите сообщение):

## Функционал: версия 1.1.1-R (03.02.20)

* Ссылку на автора забыл оставить сейчас не найду, там надо фиксить либу и код

* Пределы клавиатуры теперь составляют 10 строк и 4 кнопки на строку.
* Всего 4 цвета: primary - blue, default - white,  negative - red, positive - green
// Сначала нужно инициализировать клавиатуру а они кнопки

```java
Keyboard keys = Keyboard.of(new Button("sample", ButtonColor.DEFAULT), new Button("text", ButtonColor.NEGATIVE));
// для кнопок с ButtonColor.DEFAULT вы можете использовать String в качестве аргумента
// addButtons всегда добавляет одну новую строку и автоматически группирует кнопки по 4 в каждой строке.
// они не будут добавлять кнопки к существующим строкам
keys.addButtons("A","B","C","D","A1");
// «А1» будет автоматически перемещен на новую строку
// затем добавляем его в ответ
group.onSimpleTextMessage(message -> {
    new Message()
         .from(group)
         .to(message.authorId())
         .text(message.getText())
         .keyboard(keys)
         .send();
 });
```
<img src="https://pp.userapi.com/c851332/v851332017/126d7c/1dwV1hyu98E.jpg"></img>

Текст нажатой кнопки будет получен в message.getText(). 
Поскольку полезная нагрузка не поддерживается, вы не можете различить разные кнопки с одинаковым текстом. 
Чтобы удалить клавиатуру, используйте message.clearKeyboard() или вызовите Message.keyboard (...) с пустой клавиатурой

```java
// Реагируем на печать
group.onTyping(userId -> {
    System.out.println("Пользователь https://vk.com/id" + userId + " начал печатать");
});
    
// Печатаем сами
group.enableTyping(true);
```
* Возможность прикрепить картинку/документ/etc по ссылке/с диска/из VK:
```java
// Можно так
message.doc("doc62802565_447117479").send();

// Или так
message.doc("/Users/PeterSamokhin/Desktop/cp.zip").send();

// Или даже так
message.doc("https://www.petersamokhin.com/files/test.txt").send();
```
* Возможность загрузить обложку в сообщество одной строчкой:
```java
// В эту же группу, если при инициализации были указаны и access_token, и ID группы
group.uploadCover("https://www.petersamokhin.com/files/vk-bot-java-sdk/cover.png");
```
* Улучшено и упрощено взаимодействие с VK API: все запросы, делаемые с помощью библиотеки, напрямую или косвенно (отправкой сообщений и т.д.), становятся в очередь и выполняются с помощью метода `execute`, но можно и напрямую использовать этот метод и отдавать несколько запросов для одновременного их выполнения, синхронно или асинхронно:
```java
// Обращаемся к VK API
// Запрос будет поставлен в очередь и ответ вернётся в коллбэк
// Таким образом можно выполнять до 75 обращений к VK API в секунду
group.api().call("users.get", "{user_ids:[1,2,3]}", response -> {
     System.out.println(response);
});

// Асинхронно ставим запросы к API в очередь
JSONObject params_0 = new JSONObject();
params_0.put("user_ids", new JSONArray("[1,2,3]"));
params_0.put("fields", "photo_max_orig");
        
CallAsync call = new CallAsync("users.get", params_0, response -> {
    System.out.println(response);
});

JSONObject params_1 = new JSONObject();
params_1.put("offset", 100);
params_1.put("count", 50);

CallAsync call_1 = new CallAsync("messages.get", params_1, response -> {
    System.out.println(response);
});

// Выполняем столько запросов, сколько нам нужно
// Перечислив их через запятую в качестве параметров
group.api().execute(call_0, call_1);

// Или же синхронно
// Тогда ответы от ВК будут в массиве
// Под теми же индексами, в каком порядке были переданы запросы
JSONObject params_0 = new JSONObject();
params_0.put("user_ids", new JSONArray("[1,2,3]"));
params_0.put("fields", "photo_max_orig");

CallSync call_0 = new CallSync("users.get", params_0);

JSONObject params_1 = new JSONObject();
params_1.put("offset", 100);
params_1.put("count", 50);

CallSync call_1 = new CallSync("messages.get", params_1);

// Выводим на экран ответ на call_1
System.out.println(responses.get(1));
```
* Работаем с [Callback API](https://vk.com/dev/callback_api) ВКонтакте:
```java
// Самый простой способ - все настройки по дефолту
// Указываем только путь для прослушки запросов
// Полную и подробную настройку провести тоже можно при необходимости
group.callbackApi("/callback").onGroupJoin(newSubscriber ->
    System.out.println("Новый подписчик: https://vk.com/id" + newSubscriber.getInt("user_id"))
);
 
// Возвращён будет только object из ответа 
// (помимо него в ответе от ВК присутствует тип запроса и id группы)
group.onGroupJoin(newSubscriber ->
    System.out.println("Новый подписчик: https://vk.com/id" + newSubscriber.getInt("user_id"))
);
```
* Возможность как использовать настройки по умолчанию и написать бота в две строчки кода, так и возможность провести тонкую настройку, указать любой параметр, полностью управлять всем процессом и получать лог событий в консоль.
* В последнем обновлении библиотека стала полностью потокобезопасна благодаря внедрению `java.util.concurrent` пакета: производительность увеличена в разы, задержек при обработке сообщений нет, старые баги исправлены.
* Библиотека полностью и довольно подробно продукоментирована. В этом репозитории можно увидеть комментарии почти к каждому методу и каждому параметру, а также скомпилированы <a href="https://www.petersamokhin.com/files/vk-bot-java-sdk/javadoc/index.html">javadoc</a>.
* Убраны лишние зависимости, библиотека является самодостаточной настолько, насколько это было возможно (используется только **slf4j** и **log4j** для логгирования и **sparkjava** для обработки запросов).
## Подготовка
* Для начала необходимо создать сообщество, если бот будет работать от его имени
  * Сделать это можно [здесь](https://vk.com/groups)
* Затем необходимо получить **access_token** (_ключ доступа_)
  * Максимально подробно всё изложено [здесь](https://vk.com/dev/access_token)

## Установка
Библиотека добавлена в центральный репозиторий `maven`. Для её использования достаточно (при условии успользования любых систем сборок) добавить всего пару строк в конфигурационный файл.

#### Для maven
Добавить строки, что ниже, в **pom.xml**:
```xml
<dependency>
  <groupId>com.github.healexxzt</groupId>
  <artifactId>vk-bot-keyboard</artifactId>
  <version>1.1.1-R</version>
</dependency>
```
#### Для gradle 
Добавить строки, что ниже, в **build.gradle** в dependencies:
```gradle
implementation 'com.github.healexxzt:vk-bot-keyboard:1.1.1-R'
```
### Любые другие системы сборок
Поскольку библиотека загружена в центральный репозиторий, на сайте поиска по репозиторию описаны способы подключения библиотеки с помощью любой из систем сборок: https://mvnrepository.com/artifact/com.github.healexxzt/vk-bot-keyboard

---
#### Без систем сборок (добавляем библиотеку в classpath)
Здесь немного проще, но это не значит, что лучше. Вопрос удобства.

* Скачиваем (все зависимости включены в сборку): [библиотека (123 KB)](https://search.maven.org/remotecontent?filepath=com/github/healexxzt/vk-bot-keyboard/1.1.1-R/vk-bot-keyboard-1.1.1-R.jar)
* Теперь для использования библиотеки в проекте, нужно всего лишь добавить её в `classpath`:
  * Если компилируете через терминал, то команда будет выглядеть следующим образом: 
  ```bash
  javac -cp "/root/vk-bot-keyboard-1.1.1-R.jar" Bot.jar 
  ```
 
---
Готово. Библиотека подключена к вашему проекту и готова для использования.
