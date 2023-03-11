# java-filmorate
Template repository for Filmorate project.
![image](https://sun9-61.userapi.com/impg/OYUk_9lFIlcsoZBc9xNIMieQoGh1BJS91GRmWg/Ks403itH7tc.jpg?size=1423x771&quality=96&sign=af5225b172155dd2b1ed21c4af2112f2&type=album)

# java-filmorate

### База Данных

![Схема Базы Данных](schema.png)

#### Примеры запросов
Запрос списка фильмов в жанре триллер

*SELECT * FROM Film, Film_genres, Genres
WHERE Film.id=Film_genres.film_id AND Film_genres.genre_id=Genres.genre_id AND Genres.name='триллер'*

Запрос списка фильмов, которые понравились пользователю с логином AgentSmith

*SELECT * FROM Film,Likes,User
WHERE Film.film_id=Likes.film_id AND User.user_id=Likes.user_id AND User.login='AgentSmith'*




