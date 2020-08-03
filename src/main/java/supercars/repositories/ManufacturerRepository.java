package supercars.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import supercars.entities.Manufacturer;

import java.util.Collection;
import java.util.Optional;

public interface ManufacturerRepository extends Repository<Manufacturer, String> {
    @Query("SELECT manufacturer_id, name, web, email, logo FROM manufacturer WHERE manufacturer_id = :manufacturerId")
    Optional<Manufacturer> findById(@Param("manufacturerId") int manufacturerId);

    @Query("SELECT manufacturer_id, name, web, email, logo FROM manufacturer ORDER BY NAME")
    Collection<Manufacturer> findAllOrderByNameAsc();
}
