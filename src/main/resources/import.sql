CREATE TABLE "user" (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nickname VARCHAR(50) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        created TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE IF NOT EXISTS session (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       user_id INT NOT NULL,
                                       token VARCHAR(255) NOT NULL,
                                       expires TIMESTAMP NOT NULL,
                                       FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE IF NOT EXISTS station (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       station VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket_order (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            user_id INT NOT NULL,
                                            from_station_id INT NOT NULL,
                                            to_station_id INT NOT NULL,
                                            status INT NOT NULL,
                                            created TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                                            FOREIGN KEY (user_id) REFERENCES "user"(id),
                                            FOREIGN KEY (from_station_id) REFERENCES station(id),
                                            FOREIGN KEY (to_station_id) REFERENCES station(id)
);