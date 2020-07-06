package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class InsuranceController {
    @GetMapping("/insurance")
    public String index() {
        sleepRandomly();
        return "insurance";
    }

    private void sleepRandomly() {
        try {
            Random r = new Random();
            int i = r.nextInt(500);
            Thread.sleep(i);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
