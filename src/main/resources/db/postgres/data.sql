INSERT INTO manufacturer (manufacturer_id, name, web, email, logo) VALUES
    (1, 'Porsche', 'http://www.porsche.com', 'web@porsche.com', 'Porsche.gif'),
    (2, 'Ferrari', 'http://www.ferrari.com/en_us/', 'web@ferrari.com','Ferrari.gif'),
    (3, 'Aston Martin','http://www.astonmartin.com','web@astonmartin.com','AstonMartin.gif'),
    (4, 'BMW', 'http://www.bmw.com/com/en/', 'web@bmw.com', 'Bmw.gif'),
    (5, 'Ford', 'http://www.ford.com', 'web@ford.com', 'Ford.gif'),
    (6, 'Jaguar', 'http://www.jaguarusa.com/index.html', 'web@jaguarusa.com', 'Jaguar.gif'),
    (7, 'Lamborghini', 'http://www.lamborghini.com/en/home/', 'web@lamborghini.com', 'Lamborghini.gif'),
    (8, 'Lotus', 'http://www.lotuscars.com', 'web@lotuscars.com', 'Lotus.gif')
ON CONFLICT DO NOTHING;

SELECT pg_catalog.setval(
  'manufacturer_manufacturer_id_seq',
  COALESCE((SELECT MAX(manufacturer_id) + 1 FROM manufacturer) , 1),
  false);

INSERT INTO cars (car_id, name, model, description, manufacturer_id, colour, year, price, summary, photo) VALUES
    (1, 'Cayenne S', '4.8 V8 8AT', 'Like its sister models, the Cayenne S version was launched at the 2010 Geneva Auto Show.', 1, 'Red', 2013, 20000, 'Good', '2'),
    (2, '911 Turbo', '3.8 7AT', 'Just in time to celebrate Porsche''s 40 years anniversary since the first 911 prototype made debut, the new 2014 911 Turbo model was released', 1, 'Black', 2011, 32000, 'Fair', '3'),
    (3, 'F8 Spider', '3.9L V8 7AT', 'Designed at the same time and by the same team that worked on the F8 Tributo Berlinetta', 2, 'Dark Gray', 2014, 88000, 'Excellent', '4'),
    (4, 'Roma', '4.0L V8 8AT', 'The smallest front-engine Ferrari is also the least expensive four-wheeled product from Maranello', 2, 'Dark Gray', 2011, 48000, 'Fair', '5'),
    (5, 'Vanquish', '5.9L V12', 'Vanquish is a brand new British sportscar.', 3, 'Gold', 2015, 64000, 'Excellent', '6'),
    (6, 'V12 Speedster', '5.2L V12 Twin-Turbo 8AT', 'Gone are the days when "wind in hair" was an idiom exclusively used for convertibles.', 3, 'Black', 2019, 21000, 'Good', '7'),
    (7, 'M5', '4.4L V8 8AT xDrive AWD', 'A whole new BMW M5 was introduced in the summer of 2017', 4, 'Dark Blue', 2012, 69000, 'Good', '8'),
    (8, '6 Series Gran Turismo', '630d 8AT', 'The BMW 6 Series gained a new member in 2017 with the addition of the all-new 6 Series Gran Turismo', 4, 'Red', 2016, 47000, 'Good', '9'),
    (9, 'Kuga', '1.5L EcoBoost 6AT AWD', 'The Ford Kuga was first produced in 2008 and was also called the Ford Escape on some markets', 5, 'Black', 2015, 31000, 'Excellent', '10'),
    (10, 'Expedition', '3.5L V6 10AT', 'For the 2015 model year, Ford created a new front end for the Ford Expedition', 5, 'Light Brown', 2013, 36000, 'Excellent', '11'),
    (11, 'F-Type Coupe', '3.0L V6 8AT', 'The proportions of Jaguar are timeless and instantly recognisable, but the new F-Type looks even better.', 6, 'Light Brown', 2015, 89000, 'Good', '12'),
    (12, 'XJR', '4.2L V8 S/C 6AT', 'The new Jaguar XJ was premiered in late 2007, which was basically a facelifted version of the third generation.', 6, 'Gold', 2018, 33000, 'Fair', '13'),
    (13, 'Huracan Evo', '5.2L V10 7AT AWD', 'The 2019 Lamborghini Huracan received a facelift, an improvement for the next decade.', 7, 'White', 2019, 29000, 'Fair', '14'),
    (14, 'Urus', '4.0 V8 8AT', 'The Urus represents Lamborghini''s second attempt in the SUV market', 7, 'Silver', 2017, 79000, 'Fair', '15'),
    (15, 'Evora Sport 410', '3.5L V6 6AT', 'With the Sport 410, Lotus turns the sporty knob up to eleven in pure Colin Chapman fashion.', 8, 'Bright Red', 2019, 93000, 'Fair', '16'),
    (16, 'Exige', 'S Roadster 3.5 V6 6MT', 'Alongside the improved power to weight ratio, the new Lotus Exige S has a completely new exterior and interior.', 8, 'Gold', 2013, 57000, 'Good', '17')
ON CONFLICT DO NOTHING;

SELECT pg_catalog.setval(
  'cars_car_id_seq',
  COALESCE((SELECT MAX(car_id) + 1 FROM cars) , 1),
  false);
