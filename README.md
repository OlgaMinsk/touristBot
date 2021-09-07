# touristBot
web application for managing your own tourist telegram bot

Web приложение по управлению собственным туристическим телеграм ботом.
1) Телеграм бот выдает пользователю справочную информацию о введенном городе. Об одном городе в БД может хранится разная информация (если о городе указана разная информация, она случайным образом показывается пользователю). Например, пользователь вводит: «Москва», чат-бот отвечает: «Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))» .
2) Данные о городах хранится в базе данных MySQL.
3) Управление данными о городах (добавление новых городов и информации о них, изменение и удаление любой информации) организовано через REST WebService.

Используемые технологии: SpringBoot, SpringMVC, SpringData, Hibernate, Java 15. Для сборки проекта использован Maven.

Для запуска проекта необходимо:
в файле application.properties вместо {название бд} указать название вашей БД (например, touristguide), указать {ваш логин} и {ваш пароль}. Создание БД - в файле db. Достаточно только создать БД (CREATE SCHEMA `touristguide` ;), добавление городов и информации о них организовано через REST WebService.

bot:

username: @ReslivTourist_bot

token: 1950538374:AAEG8cDVd0CQYKAy8q22i0VfQ62tsXQOqXc
