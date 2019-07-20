CREATE TABLE cashback_index (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    genre VARCHAR(10) NOT NULL,
    sunday FLOAT(2, 2) NOT NULL,
    monday FLOAT(2, 2) NOT NULL,
    tuesday FLOAT(2, 2) NOT NULL,
    wednesday FLOAT(2, 2) NOT NULL,
    thursday FLOAT(2, 2) NOT NULL,
    friday FLOAT(2, 2) NOT NULL,
    saturday FLOAT(2, 2) NOT NULL
) ENGINE=InnoDB CHARACTER SET utf8;

CREATE UNIQUE INDEX idx_cbk_genre ON cashback_index(genre);