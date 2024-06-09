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
