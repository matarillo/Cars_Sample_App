package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import supercars.entities.Car;
import supercars.entities.Manufacturer;
import supercars.forms.CarForm;
import supercars.services.CarService;
import supercars.services.ManufacturerService;

import java.util.Collection;
import java.util.Random;

@Controller
@RequestMapping("/sell")
public class SellController {
    private final ManufacturerService manufacturerService;
    private final CarService carService;

    public SellController(ManufacturerService manufacturerService, CarService carService) {
        this.manufacturerService = manufacturerService;
        this.carService = carService;
    }

    @GetMapping
    public String load(Model model) {
        Collection<Manufacturer> manufacturers = manufacturerService.getManufacturers();
        CarForm carForm = new CarForm();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("carForm", carForm);
        sleepRandomly();
        return "sell";
    }

    @PostMapping
    public  String save(@Validated CarForm carForm,
                        BindingResult bindingResult,
                        Model model) {
        if (bindingResult.hasErrors()) {
            // TODO: validation messages and so on
            sleepRandomly();
            return "sell";
        }
        Car car = new Car();
        carForm.writeTo(car);
        carService.saveCar(car);
        model.addAttribute("actionText", "selling");
        sleepRandomly();
        return "thanks";
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
