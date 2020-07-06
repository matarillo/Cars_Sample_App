package supercars.services;

import org.springframework.stereotype.Service;
import supercars.entities.Manufacturer;
import supercars.logging.Logger;
import supercars.repositories.ManufacturerRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class ManufacturerService {
    private final ManufacturerRepository repository;

    public ManufacturerService(ManufacturerRepository repository) {
        this.repository = repository;
    }

    public Collection<Manufacturer> getManufacturers() {
        try {
            return repository.findAllOrderByNameAsc();
        } catch (Exception e) {
            Logger.log(e);
            return Collections.emptyList();
        }
    }

    public Optional<Manufacturer> getManufacturer(int manufacturerId) {
        try {
            return repository.findById(manufacturerId);
        } catch (Exception e) {
            Logger.log(e);
            return Optional.empty();
        }
    }
}
