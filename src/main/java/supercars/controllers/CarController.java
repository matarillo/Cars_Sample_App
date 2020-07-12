package supercars.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.jboss.logging.Logger;
import supercars.entities.Car;
import supercars.entities.Enquiry;
import supercars.entities.Manufacturer;
import supercars.services.CarService;
import supercars.services.EnquiryService;
import supercars.services.ManufacturerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Path("/car")
public class CarController {
    private static final Logger logger = Logger.getLogger(CarController.class);
    private final ManufacturerService manufacturerService;
    private final CarService carService;
    private final EnquiryService enquiryService;
    private final Template carView;

    @Inject
    public CarController(ManufacturerService manufacturerService,
                         CarService carService,
                         EnquiryService enquiryService,
                         @ResourcePath("car") Template carView) {
        this.manufacturerService = manufacturerService;
        this.carService = carService;
        this.enquiryService = enquiryService;
        this.carView = carView;
    }

    @GET
    public TemplateInstance get(@QueryParam("query") String query,
                                @QueryParam("cid") String carIdParam) {
        Optional<Integer> optCarId = tryParseInt(carIdParam);
        if (optCarId.isPresent()) {
            int carId = optCarId.get();
            Optional<Car> optCar = carService.getCar(carId);
            if (optCar.isPresent()) {
                Car car = optCar.get();
                Optional<Manufacturer> optManufacturer = manufacturerService.getManufacturer(car.getManufacturerId());
                if (!optManufacturer.isPresent()) {
                    throw new InternalServerErrorException("resource is inconsistent");
                }
                Manufacturer manufacturer = optManufacturer.get();
                Collection<Enquiry> enquiries = null;
                if (query != null && query.equals("carEnquiries")) {
                    enquiries = enquiryService.getEnquiriesForCar(carId);
                }
                sleepRandomly();
                return carView
                        .data("car", car)
                        .data("manufacturer", manufacturer)
                        .data("enquiries", enquiries);
            }
        }
        throw new NotFoundException("Unable to find resource");
    }

    private Optional<Integer> tryParseInt(String value) {
        try {
            int i = Integer.parseInt(value);
            return Optional.of(i);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private void sleepRandomly() {
        try {
            Random r = new Random();
            int i = r.nextInt(2000);
            Thread.sleep(i);
        } catch (Exception e) {
            logger.warn(e);
        }
    }
}
