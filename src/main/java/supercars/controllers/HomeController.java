package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import supercars.externaldata.FuelPrices;
import supercars.services.FuelService;

@Controller
public class HomeController {
    private final FuelService fuelService;

    public HomeController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        FuelPrices prices = fuelService.getFuelPrices();
        model.addAttribute("prices", prices);
        return "home";
    }
}
