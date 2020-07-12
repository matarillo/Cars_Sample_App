package supercars.services;

import org.jboss.logging.Logger;
import supercars.ApplicationException;
import supercars.entities.Car;
import supercars.repositories.CarRepository;

import javax.enterprise.context.RequestScoped;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RequestScoped
public class CarService {
    private static final Logger logger = Logger.getLogger(CarService.class);
    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car saveCar(Car car) {
        try {
            car = repository.save(car);
            logger.debug(String.format("car.enquiryId=%d", car.getCarId()));
            logger.debug(String.format("car.name=%s", car.getName()));
            logger.debug(String.format("car.model=%s", car.getModel()));
            logger.debug(String.format("car.description=%s", car.getDescription()));
            logger.debug(String.format("car.manufacturerId=%d", car.getManufacturerId()));
            logger.debug(String.format("car.colour=%s", car.getColour()));
            logger.debug(String.format("car.year=%d", car.getYear()));
            logger.debug(String.format("car.price=%f", car.getPrice()));
            logger.debug(String.format("car.summary=%s", car.getSummary()));
            logger.debug(String.format("car.photo=%s", car.getPhoto()));
            // return car;
            throw new ApplicationException("Example Exception Thrown");
        } catch (Exception e) {
            logger.error(e);
            return car;
        }
    }

    public Optional<Car> getCar(int carId) {
        try {
            return repository.findById(carId);
        } catch (Exception e) {
            logger.error(e);
            return Optional.empty();
        }
    }

    public Collection<Car> getCarsBySearch(String criteria) {
        try {
            String query = isEmpty(criteria) ? "%" : "%" + criteria + "%";
            return repository.findAllByPartialMatch(query);
        } catch (Exception e) {
            logger.error(e);
            return Collections.emptyList();
        }
    }

    private boolean isEmpty(String value) {
        return (value == null) || (value.length() == 0);
    }

    public Collection<Car> getCarsByManufacturer(int manufacturerId) {
        try {
            return repository.findAllByManufacturerId(manufacturerId);
        } catch (Exception e) {
            logger.error(e);
            return Collections.emptyList();
        }
    }
}
