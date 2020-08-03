package supercars.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import supercars.entities.Car;

import java.util.Collection;
import java.util.Optional;

public interface CarRepository extends Repository<Car, String>  {
    @Query("SELECT car_id, name, model, summary, description, manufacturer_id, colour, year, price, photo FROM cars WHERE car_id = :carId")
    Optional<Car> findById(@Param("carId") int carId);

    @Query("SELECT car_id, name, model, summary, description, manufacturer_id, colour, year, price, photo FROM cars WHERE manufacturer_id = :manufacturerId")
    Collection<Car> findAllByManufacturerId(@Param("manufacturerId") int manufacturerId);

    @Query("SELECT car_id, c.name, model, summary, description, m.manufacturer_id, colour, year, price, photo FROM cars C, manufacturer M WHERE C.manufacturer_id = M.manufacturer_id AND (C.name LIKE :query OR C.model LIKE :query OR M.name LIKE :query)")
    Collection<Car> findAllByPartialMatch(@Param("query") String query);

    Car save(Car car);
}
