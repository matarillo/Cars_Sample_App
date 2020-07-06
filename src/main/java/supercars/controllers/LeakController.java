package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import supercars.forms.EnquiryForm;
import supercars.forms.LeakForm;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/leak")
public class LeakController {
    public static final List<byte[]> leakyCollection = new LinkedList<>();

    @GetMapping
    public String index(Model model) {
        sleepRandomly();
        return "leak";
    }

    @PostMapping
    public String save(@Validated LeakForm leakForm,
                       BindingResult bindingResult,
                       Model model) {
        if (!bindingResult.hasErrors()) {
            addToCollection(leakForm.getNumber(), leakForm.getSize());
        }
        model.addAttribute("actionText", "selling");
        sleepRandomly();
        return "thanks";
    }

    private void addToCollection(int number, int size) {
        for (int i = 0; i < number; i++) {
            LeakController.leakyCollection.add(new byte[size]);
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
