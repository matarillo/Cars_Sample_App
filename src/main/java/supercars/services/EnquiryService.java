package supercars.services;

import org.jboss.logging.Logger;
import supercars.entities.Enquiry;
import supercars.repositories.EnquiryRepository;

import javax.enterprise.context.RequestScoped;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RequestScoped
public class EnquiryService {
    private static final Logger logger = Logger.getLogger(EnquiryService.class);
    private final EnquiryRepository repository;

    public EnquiryService(EnquiryRepository repository) {
        this.repository = repository;
    }

    public Optional<Enquiry> getEnquiry(int enquiryId) {
        try {
            return repository.findById(enquiryId);
        } catch (Exception e) {
            logger.error(e);
            return Optional.empty();
        }
    }

    public Collection<Enquiry> getEnquiriesForCar(int carId) {
        try {
            return repository.findAllByCarId(carId);
        } catch (Exception e) {
            logger.error(e);
            return Collections.emptyList();
        }
    }

    public Enquiry saveEnquiry(Enquiry enquiry) {
        try {
            enquiry = repository.save(enquiry);
            logger.debug(String.format("enquiry.enquiryId=%d", enquiry.getEnquiryId()));
            logger.debug(String.format("enquiry.name=%s", enquiry.getName()));
            logger.debug(String.format("enquiry.email=%s", enquiry.getEmail()));
            logger.debug(String.format("enquiry.comment=%s", enquiry.getComment()));
            logger.debug(String.format("enquiry.carId=%d", enquiry.getCarId()));
            logger.debug(String.format("enquiry.dummy=%d", enquiry.getDummy()));
            // enquiry.setEnquiryId(0);
            return enquiry;
        } catch (Exception e) {
            logger.error(e);
            return enquiry;
        }
    }
}
