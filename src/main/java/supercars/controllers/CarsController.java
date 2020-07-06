package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import supercars.entities.Car;
import supercars.entities.Manufacturer;
import supercars.services.CarService;
import supercars.services.ManufacturerService;

import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class CarsController {
    private final ManufacturerService manufacturerService;
    private final CarService carService;

    public CarsController(ManufacturerService manufacturerService, CarService carService) {
        this.manufacturerService = manufacturerService;
        this.carService = carService;
    }

    @GetMapping("/search")
    public  String search(@RequestParam(name = "query", required = false) String query,
                          @RequestParam(name = "criteria", required = false) String criteria,
                          Model model) {
        if (query == null) {
        } else if (query.equals("search")) {
            getCarsBySearch(criteria, model);
        }
        sleepRandomly();
        return "search";
    }

    @GetMapping("/cars")
    public String manufacturer(@RequestParam(name = "query", required = false) String query,
                               @RequestParam(name = "mid", required = false) String manuId,
                               Model model) {
        if (query == null) {
        } else if (query.equals("manu")) {
            getCarsByManufacturer(manuId, model);
        }
        sleepRandomly();
        return "search";
    }

    public void getCarsByManufacturer(String manuIdParam, Model model) {
        Optional<Integer> manuId = tryParseInt(manuIdParam);
        if (manuId.isPresent()) {
            Optional<Manufacturer> manufacturer = manufacturerService.getManufacturer(manuId.get());
            if (manufacturer.isPresent()) {
                Collection<Car> cars = carService.getCarsByManufacturer(manuId.get());
                model.addAttribute("manufacturer", manufacturer.get());
                model.addAttribute("cars", cars);
                return;
            }
        }
        throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
    }

    public void getCarsBySearch(String criteria, Model model) {
        Map<Integer, Manufacturer> manufacturers = new HashMap<>();
        Collection<Car> cars = carService.getCarsBySearch(criteria);
        for (Car car : cars) {
            Optional<Manufacturer> manufacturer = manufacturerService.getManufacturer(car.getManufacturerId());
            if (manufacturer.isPresent()) {
                manufacturers.put(car.getManufacturerId(), manufacturer.get());
            }
        }
        model.addAttribute("criteria", criteria);
        model.addAttribute("cars", cars);
        model.addAttribute("manufacturers", manufacturers);
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
            int i = r.nextInt(999);
            Thread.sleep(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
