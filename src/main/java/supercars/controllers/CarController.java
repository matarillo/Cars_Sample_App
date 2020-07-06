package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import supercars.entities.Car;
import supercars.entities.Enquiry;
import supercars.entities.Manufacturer;
import supercars.services.CarService;
import supercars.services.EnquiryService;
import supercars.services.ManufacturerService;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Controller
public class CarController {
    private final ManufacturerService manufacturerService;
    private final CarService carService;
    private final EnquiryService enquiryService;

    public CarController(ManufacturerService manufacturerService, CarService carService, EnquiryService enquiryService) {
        this.manufacturerService = manufacturerService;
        this.carService = carService;
        this.enquiryService = enquiryService;
    }

    @GetMapping("/car")
    public String index(@RequestParam(name = "query", required = false) String query,
                        @RequestParam(name = "cid", required = false) String carIdParam,
                        Model model) {
        Optional<Integer> optCarId = tryParseInt(carIdParam);
        if (optCarId.isPresent()) {
            int carId = optCarId.get();
            Optional<Car> optCar = carService.getCar(carId);
            if (optCar.isPresent()) {
                Car car = optCar.get();
                Optional<Manufacturer> optManufacturer = manufacturerService.getManufacturer(car.getManufacturerId());
                if (!optManufacturer.isPresent()) {
                    throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "resource is inconsistent");
                }
                Manufacturer manufacturer = optManufacturer.get();
                Collection<Enquiry> enquiries = null;
                if (query != null && query.equals("carEnquiries")) {
                    enquiries = enquiryService.getEnquiriesForCar(carId);
                }
                model.addAttribute("car", car);
                model.addAttribute("manufacturer", manufacturer);
                model.addAttribute("enquiries", enquiries);

                sleepRandomly();
                return "car";
            }
        }
        throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
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
            e.printStackTrace();
        }
    }
}
