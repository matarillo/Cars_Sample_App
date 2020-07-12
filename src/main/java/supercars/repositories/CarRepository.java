package supercars.repositories;

import io.agroal.api.AgroalDataSource;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import supercars.entities.Car;

import javax.enterprise.context.RequestScoped;
import java.util.Collection;
import java.util.Optional;

@RequestScoped
public class CarRepository {
    private final AgroalDataSource dataSource;

    public CarRepository(AgroalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Car> findById(int carId) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT CAR_ID, NAME, MODEL, SUMMARY, DESCRIPTION, MANUFACTURER_ID, COLOUR, YEAR, PRICE, PHOTO FROM CARS WHERE CAR_ID = :carId")
                    .addParameter("carId", carId)
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetch(Car.class)
                    .stream()
                    .findFirst();
        }
    }

    public Collection<Car> findAllByManufacturerId(int manufacturerId) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT CAR_ID, NAME, MODEL, SUMMARY, DESCRIPTION, MANUFACTURER_ID, COLOUR, YEAR, PRICE, PHOTO FROM CARS WHERE MANUFACTURER_ID = :manufacturerId")
                    .addParameter("manufacturerId", manufacturerId)
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetch(Car.class);
        }
    }

    public Collection<Car> findAllByPartialMatch(String query) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT CAR_ID, C.NAME, MODEL, SUMMARY, DESCRIPTION, M.MANUFACTURER_ID, COLOUR, YEAR, PRICE, PHOTO FROM CARS C, MANUFACTURER M WHERE C.MANUFACTURER_ID = M.MANUFACTURER_ID AND (C.NAME LIKE :query OR C.MODEL LIKE :query OR M.NAME LIKE :query)")
                    .addParameter("query", query)
                    .setAutoDeriveColumnNames(true)
                    .executeAndFetch(Car.class);
        }
    }

    public  Car save(Car car) {
        if (car.getCarId() == null) {
            return insert(car);
        } else {
            return update(car);
        }
    }

    public Car update(Car car) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            connection.createQuery("UPDATE CARS SET NAME = :name, MODEL = :model, SUMMARY = :summary, DESCRIPTION = :description, MANUFACTURER_ID = :manufacturerId, COLOUR = :colour, YEAR = :year, PRICE = :price, PHOTO = :photo WHERE CAR_ID = :carId")
                    .setAutoDeriveColumnNames(true)
                    .bind(car)
                    .executeUpdate();
            Car newCar = car.clone();
            return newCar;
        }
    }

    public  Car insert(Car car) {
        Sql2o sql2o = new Sql2o(this.dataSource);
        try (Connection connection = sql2o.open()) {
            Object carId = connection.createQuery("INSERT INTO CARS (NAME, MODEL, SUMMARY, DESCRIPTION, MANUFACTURER_ID, COLOUR, YEAR, PRICE, PHOTO) VALUES (:name, :model, :summary, :description, :manufacturerId, :colour, :year, :price, :photo)", true)
                    .setAutoDeriveColumnNames(true)
                    .bind(car)
                    .executeUpdate()
                    .getKey();
            car.setCarId((Integer)carId);
            return car;
        }
    }
}
