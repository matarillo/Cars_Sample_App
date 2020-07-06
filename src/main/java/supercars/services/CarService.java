package supercars.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import supercars.ApplicationException;
import supercars.entities.Car;
import supercars.logging.LogLevel;
import supercars.logging.Logger;
import supercars.repositories.CarRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car saveCar(Car car) {
        try {
            car = repository.save(car);
            Logger.log(String.format("car.enquiryId=%d", car.getCarId()), LogLevel.DEBUG);
            Logger.log(String.format("car.name=%s", car.getName()), LogLevel.DEBUG);
            Logger.log(String.format("car.model=%s", car.getModel()), LogLevel.DEBUG);
            Logger.log(String.format("car.description=%s", car.getDescription()), LogLevel.DEBUG);
            Logger.log(String.format("car.manufacturerId=%d", car.getManufacturerId()), LogLevel.DEBUG);
            Logger.log(String.format("car.colour=%s", car.getColour()), LogLevel.DEBUG);
            Logger.log(String.format("car.year=%d", car.getYear()), LogLevel.DEBUG);
            Logger.log(String.format("car.price=%f", car.getPrice()), LogLevel.DEBUG);
            Logger.log(String.format("car.summary=%s", car.getSummary()), LogLevel.DEBUG);
            Logger.log(String.format("car.photo=%s", car.getPhoto()), LogLevel.DEBUG);
            // return car;
            throw new ApplicationException("Example Exception Thrown");
        } catch (Exception e) {
            Logger.log(e);
            return car;
        }
    }

    public Optional<Car> getCar(int carId) {
        try {
            return repository.findById(carId);
        } catch (Exception e) {
            Logger.log(e);
            return Optional.empty();
        }
    }

    public Collection<Car> getCarsBySearch(String criteria) {
        try {
            String query = StringUtils.isEmpty(criteria) ? "%" : "%" + criteria + "%";
            return repository.findAllByPartialMatch(query);
        } catch (Exception e) {
            Logger.log(e);
            return Collections.emptyList();
        }
    }

    public Collection<Car> getCarsByManufacturer(int manufacturerId) {
        try {
            return repository.findAllByManufacturerId(manufacturerId);
        } catch (Exception e) {
            Logger.log(e);
            return Collections.emptyList();
        }
    }
}
