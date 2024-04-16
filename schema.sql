-- CREATE Database
CREATE DATABASE activify_database;

-- Create table for User
CREATE TABLE User (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255),
                      email VARCHAR(255),
                      paswword VARCHAR(255)
);
-- Create table for Activity
CREATE TABLE Activity (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          distance DECIMAL(10, 2),
                          duration TIME,
                          elevation INT,
                          sport VARCHAR(255),
                          date DATE,
                          title VARCHAR(255),
                          description TEXT,
                          route_type VARCHAR(255),
                          user_id INT,
                          FOREIGN KEY (user_id) REFERENCES User(id)
);