package supercars.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import supercars.entities.Manufacturer;

import java.util.Collection;
import java.util.Optional;

public interface ManufacturerRepository extends Repository<Manufacturer, String> {
    @Query("SELECT MANUFACTURER_ID, NAME, WEB, EMAIL, LOGO FROM MANUFACTURER WHERE MANUFACTURER_ID = :manufacturerId")
    Optional<Manufacturer> findById(@Param("manufacturerId") int manufacturerId);

    @Query("SELECT MANUFACTURER_ID, NAME, WEB, EMAIL, LOGO FROM MANUFACTURER ORDER BY NAME")
    Collection<Manufacturer> findAllOrderByNameAsc();
}
