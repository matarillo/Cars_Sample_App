package supercars.services;

import org.jboss.logging.Logger;
import supercars.entities.Manufacturer;
import supercars.repositories.ManufacturerRepository;

import javax.enterprise.context.RequestScoped;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RequestScoped
public class ManufacturerService {
    private static final Logger logger = Logger.getLogger(ManufacturerService.class);
    private final ManufacturerRepository repository;

    public ManufacturerService(ManufacturerRepository repository) {
        this.repository = repository;
    }

    public Collection<Manufacturer> getManufacturers() {
        try {
            return repository.findAllOrderByNameAsc();
        } catch (Exception e) {
            logger.error(e);
            return Collections.emptyList();
        }
    }

    public Optional<Manufacturer> getManufacturer(int manufacturerId) {
        try {
            return repository.findById(manufacturerId);
        } catch (Exception e) {
            logger.error(e);
            return Optional.empty();
        }
    }
}
