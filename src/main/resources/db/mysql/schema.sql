DROP TABLE IF EXISTS MANUFACTURER;

CREATE TABLE IF NOT EXISTS MANUFACTURER (
    MANUFACTURER_ID MEDIUMINT NOT NULL,
    NAME VARCHAR(30),
    WEB VARCHAR(50),
    EMAIL VARCHAR(50),
    LOGO VARCHAR(30),
    PRIMARY KEY (MANUFACTURER_ID)
);

DROP TABLE IF EXISTS CARS;

CREATE TABLE IF NOT EXISTS CARS (
     CAR_ID MEDIUMINT NOT NULL AUTO_INCREMENT,
     NAME VARCHAR(30),
     MODEL VARCHAR(30),
     DESCRIPTION VARCHAR(200),
     MANUFACTURER_ID MEDIUMINT NOT NULL,
     COLOUR VARCHAR(20),
     YEAR MEDIUMINT,
     PRICE FLOAT,
     SUMMARY VARCHAR(200),
     PHOTO VARCHAR(30),
     PRIMARY KEY (CAR_ID)
);

DROP TABLE IF EXISTS ENQUIRIES;

CREATE TABLE IF NOT EXISTS ENQUIRIES (
    ENQUIRY_ID MEDIUMINT NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(50),
    EMAIL VARCHAR(50),
    COMMENT VARCHAR(200),
    CAR_ID MEDIUMINT,
    DUMMY MEDIUMINT,
    PRIMARY KEY (ENQUIRY_ID)
);