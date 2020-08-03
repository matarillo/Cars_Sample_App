package supercars.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import supercars.entities.Enquiry;

import java.util.Collection;
import java.util.Optional;

public interface EnquiryRepository extends Repository<Enquiry, String> {
    @Query("SELECT enquiry_id, name, email, comment, car_id FROM enquiries WHERE enquiry_id = :enquiryId")
    Optional<Enquiry> findById(@Param("enquiryId") int enquiryId);

    @Query("SELECT enquiry_id, name, email, comment, car_id FROM enquiries WHERE car_id = :carId")
    Collection<Enquiry> findAllByCarId(@Param("carId") int carId);

    Enquiry save(Enquiry enquiry);
}
