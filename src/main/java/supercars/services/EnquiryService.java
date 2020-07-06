package supercars.services;

import org.springframework.stereotype.Service;
import supercars.entities.Enquiry;
import supercars.logging.LogLevel;
import supercars.logging.Logger;
import supercars.repositories.EnquiryRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class EnquiryService {
    private final EnquiryRepository repository;

    public EnquiryService(EnquiryRepository repository) {
        this.repository = repository;
    }

    public Optional<Enquiry> getEnquiry(int enquiryId) {
        try {
            return repository.findById(enquiryId);
        } catch (Exception e) {
            Logger.log(e);
            return Optional.empty();
        }
    }

    public Collection<Enquiry> getEnquiriesForCar(int carId) {
        try {
            return repository.findAllByCarId(carId);
        } catch (Exception e) {
            Logger.log(e);
            return Collections.emptyList();
        }
    }

    public Enquiry saveEnquiry(Enquiry enquiry) {
        try {
            enquiry = repository.save(enquiry);
            Logger.log(String.format("enquiry.enquiryId=%d", enquiry.getEnquiryId()), LogLevel.DEBUG);
            Logger.log(String.format("enquiry.name=%s", enquiry.getName()), LogLevel.DEBUG);
            Logger.log(String.format("enquiry.email=%s", enquiry.getEmail()), LogLevel.DEBUG);
            Logger.log(String.format("enquiry.comment=%s", enquiry.getComment()), LogLevel.DEBUG);
            Logger.log(String.format("enquiry.carId=%d", enquiry.getCarId()), LogLevel.DEBUG);
            Logger.log(String.format("enquiry.dummy=%d", enquiry.getDummy()), LogLevel.DEBUG);
            // enquiry.setEnquiryId(0);
            return enquiry;
        } catch (Exception e) {
            Logger.log(e);
            return enquiry;
        }
    }
}
