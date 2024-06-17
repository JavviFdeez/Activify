-- CREATE Database
CREATE DATABASE activify_database;

-- Create table for User
CREATE TABLE user (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255),
                      password VARCHAR(255)
);
-- Create table for Activity
CREATE TABLE activity (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          distance DECIMAL(10, 2),
                          duration TIME,
                          elevation INT,
                          sport VARCHAR(255),
                          date DATE,
                          title VARCHAR(255),
                          user_id INT,
                          FOREIGN KEY (user_id) REFERENCES User(id)
);