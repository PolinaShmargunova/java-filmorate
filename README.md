# java-filmorate
Template repository for Filmorate project.
![image](https://user-images.githubusercontent.com/113606130/222157385-7684e9dd-31dc-4351-9d31-248e8bb89f06.png)

# java-filmorate

### База Данных

![Схема Базы Данных](schema.png)

#### Таблицы
- **User** список пользователей
- **Film** список фильмов
- **Friends_list** связи дружбы между пользователями: *user_id* - id пользователя, *fritnd_id* - id друга, *friendship_status* - статус подтверждения дружбы
- **Likes** лайки пользователей: *film_id* - id фильма, *user_id* - id пользователя поставившего лайк
- **MPA** рейтинг Ассоциации кинокомпаний
- **Genres** список жанров
- **Film_genres** список жанров фильмов: *film_id* - id фильма, *genre_id* - id жанра

#### Примеры запросов
Запрос списка фильмов в жанре триллер

*SELECT * FROM Film, Film_genres, Genres
WHERE Film.id=Film_genres.film_id AND Film_genres.genre_id=Genres.genre_id AND Genres.name='триллер'*

Запрос списка фильмов, которые понравились пользователю с логином AgentSmith

*SELECT * FROM Film,Likes,User
WHERE Film.film_id=Likes.film_id AND User.user_id=Likes.user_id AND User.login='AgentSmith'*




