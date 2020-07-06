package supercars.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import supercars.entities.Enquiry;

import java.util.Collection;
import java.util.Optional;

public interface EnquiryRepository extends Repository<Enquiry, String> {
    @Query("SELECT ENQUIRY_ID, NAME, EMAIL, COMMENT, CAR_ID FROM ENQUIRIES WHERE ENQUIRY_ID = :enquiryId")
    Optional<Enquiry> findById(@Param("enquiryId") int enquiryId);

    @Query("SELECT ENQUIRY_ID, NAME, EMAIL, COMMENT, CAR_ID FROM ENQUIRIES WHERE CAR_ID = :carId")
    Collection<Enquiry> findAllByCarId(@Param("carId") int carId);

    Enquiry save(Enquiry enquiry);
}
