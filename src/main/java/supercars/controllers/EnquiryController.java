package supercars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import supercars.entities.Enquiry;
import supercars.forms.EnquiryForm;
import supercars.services.EnquiryService;

import java.util.Optional;
import java.util.Random;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/enquire")
public class EnquiryController {
    private final EnquiryService enquiryService;

    public EnquiryController(EnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @GetMapping
    public String index(@RequestParam(name = "cid", required = false) Integer cid,
                        @RequestParam(name = "carName", required = false) String carName,
                        Model model) {
        EnquiryForm enquiryForm = new EnquiryForm();
        enquiryForm.setCid(cid);
        enquiryForm.setCarName(carName);
        model.addAttribute("enquiryForm", enquiryForm);
        sleepRandomly();
        return "enquire";
    }

    @GetMapping(params = {"query=load"})
    public String load(@RequestParam(name = "enquireId") int enquireId,
                       Model model) {
        Optional<Enquiry> optEnquiry = enquiryService.getEnquiry(enquireId);
        if (optEnquiry.isPresent()) {
            Enquiry enquiry = optEnquiry.get();
            EnquiryForm enquiryForm = new EnquiryForm();
            enquiryForm.readFrom(enquiry);
            model.addAttribute("enquiryForm", enquiryForm);
            sleepRandomly();
            return "enquire";
        }
        throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
    }

    @PostMapping(params = {"query=save"})
    public String save(@Validated EnquiryForm enquiryForm,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            // TODO: validation messages and so on
            sleepRandomly();
            return "enquire";
        }
        Enquiry enquiry = new Enquiry();
        enquiryForm.writeTo(enquiry);
        enquiryService.saveEnquiry(enquiry);
        model.addAttribute("actionText", "enquiring");
        sleepRandomly();
        return "thanks";
    }

    private void sleepRandomly() {
        try {
            Random r = new Random();
            int i = r.nextInt(999);
            Thread.sleep(i);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
