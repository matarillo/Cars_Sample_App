package supercars.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import supercars.entities.Car;

import java.util.Collection;
import java.util.Optional;

public interface CarRepository extends Repository<Car, String>  {
    @Query("SELECT CAR_ID, NAME, MODEL, SUMMARY, DESCRIPTION, MANUFACTURER_ID, COLOUR, YEAR, PRICE, PHOTO FROM CARS WHERE CAR_ID = :carId")
    Optional<Car> findById(@Param("carId") int carId);

    @Query("SELECT CAR_ID, NAME, MODEL, SUMMARY, DESCRIPTION, MANUFACTURER_ID, COLOUR, YEAR, PRICE, PHOTO FROM CARS WHERE MANUFACTURER_ID = :manufacturerId")
    Collection<Car> findAllByManufacturerId(@Param("manufacturerId") int manufacturerId);

    @Query("SELECT CAR_ID, C.NAME, MODEL, SUMMARY, DESCRIPTION, M.MANUFACTURER_ID, COLOUR, YEAR, PRICE, PHOTO FROM CARS C, MANUFACTURER M WHERE C.MANUFACTURER_ID = M.MANUFACTURER_ID AND (C.NAME LIKE :query OR C.MODEL LIKE :query OR M.NAME LIKE :query)")
    Collection<Car> findAllByPartialMatch(@Param("query") String query);

    Car save(Car car);
}
