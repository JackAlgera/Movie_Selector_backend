CREATE TABLE IF NOT EXISTS movie
(
    id INTEGER NOT NULL PRIMARY KEY,
    title VARCHAR (255) DEFAULT NULL,
    overview VARCHAR DEFAULT NULL,
    posterPath VARCHAR (255) DEFAULT NULL,
    releaseDate VARCHAR (255) DEFAULT NULL,
    lastModified TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS genre
(
    id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    lastModified TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS movie_genres
(
    movieId INTEGER NOT NULL,
    genreId INTEGER NOT NULL,
    PRIMARY KEY (movieId, genreId),
    FOREIGN KEY (movieId)
        REFERENCES movie (id)
        ON DELETE CASCADE,
    FOREIGN KEY (genreId)
        REFERENCES genre (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS room
(
    roomId VARCHAR (20) NOT NULL PRIMARY KEY,
    foundMovieId INTEGER,
    createdOn TIMESTAMP NOT NULL,
    lastModified TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    userId uuid NOT NULL PRIMARY KEY,
    userName VARCHAR (255) NOT NULL,
    roomId VARCHAR (20),
    createdOn TIMESTAMP NOT NULL,
    lastModified TIMESTAMP NOT NULL,
    FOREIGN KEY (roomId)
        REFERENCES room (roomId)
            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS selection
(
    userId uuid NOT NULL,
    roomId VARCHAR (20) NOT NULL,
    movieId INTEGER NOT NULL,
    rating INTEGER NOT NULL,
    createdOn TIMESTAMP NOT NULL,
    PRIMARY KEY (userId, roomId, movieId),
    FOREIGN KEY (userId)
        REFERENCES users (userId)
            ON DELETE CASCADE,
    FOREIGN KEY (roomId)
        REFERENCES room (roomId)
            ON DELETE CASCADE,
    FOREIGN KEY (movieId)
        REFERENCES movie (id)
            ON DELETE CASCADE
);
