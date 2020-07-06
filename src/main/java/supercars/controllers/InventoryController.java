package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import supercars.entities.Manufacturer;
import supercars.services.ManufacturerService;

import java.util.Random;

@Controller
public class InventoryController {
    private final ManufacturerService manufacturerService;

    public InventoryController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/supercars")
    public String index(Model model) {
        Iterable<Manufacturer> manufacturers = manufacturerService.getManufacturers();
        model.addAttribute("manufacturers", manufacturers);
        sleepRandomly();
        return "inventory";
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
