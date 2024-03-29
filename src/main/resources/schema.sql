CREATE TABLE IF NOT EXISTS MPA (
                                   id INTEGER PRIMARY KEY auto_increment,
                                   name  VARCHAR(128),
    description  VARCHAR(128)
    );
CREATE TABLE IF NOT EXISTS FILMS (
                                     id INTEGER PRIMARY KEY auto_increment,
                                     name  VARCHAR(128),
    description VARCHAR(200),
    releaseDate DATE,
    duration LONG,
    rate INTEGER,
    mpaId INTEGER,
    FOREIGN KEY (mpaId) REFERENCES MPA (id) ON DELETE SET NULL
    );
CREATE TABLE IF NOT EXISTS USERS (
                                     id INTEGER PRIMARY KEY auto_increment,
                                     email VARCHAR(128),
    login VARCHAR(128),
    name VARCHAR(128),
    birthday DATE
    );
CREATE TABLE IF NOT EXISTS GENRES (
                                      id INTEGER PRIMARY KEY auto_increment,
                                      name  VARCHAR(128)
    );
CREATE TABLE IF NOT EXISTS LIKES (
                                     filmId INTEGER,
                                     userId INTEGER,
                                     PRIMARY KEY (filmId,userId),
    FOREIGN KEY (filmId)  REFERENCES FILMS (id) ON DELETE CASCADE,
    FOREIGN KEY (userId)  REFERENCES USERS (id) ON DELETE CASCADE
    );
CREATE TABLE IF NOT EXISTS FRIENDS (
                                       userId INTEGER,
                                       friendId INTEGER,
                                       status BOOLEAN,
                                       PRIMARY KEY (userId,friendId),
    FOREIGN KEY (userId)    REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (friendId)  REFERENCES USERS (id) ON DELETE CASCADE
    );
CREATE TABLE IF NOT EXISTS FILMGENRES (
                                          filmId INTEGER,
                                          genreId INTEGER,
                                          PRIMARY KEY (filmId,genreId),
    FOREIGN KEY (filmId)  REFERENCES FILMS (id) ON DELETE CASCADE,
    FOREIGN KEY (genreId)  REFERENCES GENRES (id) ON DELETE CASCADE
    );
