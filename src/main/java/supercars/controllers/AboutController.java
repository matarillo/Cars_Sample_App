package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String index() {
        sleepRandomly();
        return "about";
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
