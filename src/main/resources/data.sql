CREATE TABLE IF NOT EXISTS `account` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    loggedIn BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS `movie` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    genre VARCHAR(250) NOT NULL,
    runtime INT NOT NULL
);

CREATE TABLE IF NOT EXISTS `room` (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    numberOfRows INT NOT NULL,
    numberOfCols INT NOT NULL
);