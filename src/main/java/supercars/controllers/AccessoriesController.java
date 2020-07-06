package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class AccessoriesController {

    @GetMapping("/accessories/{name}")
    public String index(@PathVariable("name") String name) {
        if (name != null) {
            switch (name) {
                case "amg":
                case "alpina":
                case "gembella":
                case "mazdaspeed":
                case "ruf":
                    sleepRandomly();
                    return "accessories/" + name;
                default:
                    break;
            }
        }
        throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
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
