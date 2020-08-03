DROP TABLE IF EXISTS manufacturer;

CREATE TABLE IF NOT EXISTS manufacturer (
    manufacturer_id SERIAL PRIMARY KEY,
    name VARCHAR(30),
    web VARCHAR(50),
    email VARCHAR(50),
    logo VARCHAR(30)
);

DROP TABLE IF EXISTS cars;

CREATE TABLE IF NOT EXISTS cars (
     car_id SERIAL PRIMARY KEY,
     name VARCHAR(30),
     model VARCHAR(30),
     description VARCHAR(200),
     manufacturer_id INT NOT NULL,
     colour VARCHAR(20),
     year SMALLINT,
     price FLOAT,
     summary VARCHAR(200),
     photo VARCHAR(30)
);

DROP TABLE IF EXISTS enquiries;

CREATE TABLE IF NOT EXISTS enquiries (
    enquiry_id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50),
    comment VARCHAR(200),
    car_id INT,
    dummy INT
);
