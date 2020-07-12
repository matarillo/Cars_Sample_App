package supercars.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.Form;
import supercars.entities.Enquiry;
import supercars.forms.EnquiryForm;
import supercars.services.EnquiryService;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Path("/enquire")
public class EnquiryController {
    private static final Logger logger = Logger.getLogger(EnquiryController.class);
    private final EnquiryService enquiryService;
    private final Validator validator;
    private final Template enquireView;
    private final Template thanksView;

    @Inject
    public EnquiryController(EnquiryService enquiryService,
                             Validator validator,
                             @ResourcePath("enquire") Template enquireView,
                             @ResourcePath("thanks") Template thanksView) {
        this.enquiryService = enquiryService;
        this.validator = validator;
        this.enquireView = enquireView;
        this.thanksView = thanksView;
    }

    @GET
    public TemplateInstance get(@QueryParam("query") String query,
                                @QueryParam("cid") String cid,
                                @QueryParam("carName") String carName,
                                @QueryParam("enquireId") String enquireId) {
        if (query != null && query.equals("load")) {
            return load(enquireId);
        } else {
            return enquireCar(cid, carName);
        }
    }

    public TemplateInstance enquireCar(String cid, String carName) {
        EnquiryForm enquiryForm = new EnquiryForm();
        enquiryForm.setCid(cid);
        enquiryForm.setCarName(carName);
        return enquire(enquiryForm);
    }

    private TemplateInstance enquire(EnquiryForm enquiryForm) {
        sleepRandomly();
        return enquireView.data("enquiryForm", enquiryForm);
    }

    public TemplateInstance load(String enquireIdParam) {
        if (enquireIdParam == null) {
            throw new NotFoundException("Unable to find resource");
        }
        int enquireId = 0;
        try {
            enquireId = Integer.parseInt(enquireIdParam);
        } catch (NumberFormatException e) {
            throw new NotFoundException("Unable to find resource");
        }
        Optional<Enquiry> optEnquiry = enquiryService.getEnquiry(enquireId);
        if (optEnquiry.isEmpty()) {
            throw new NotFoundException("Unable to find resource");
        }
        Enquiry enquiry = optEnquiry.get();
        EnquiryForm enquiryForm = new EnquiryForm();
        enquiryForm.readFrom(enquiry);
        return enquire(enquiryForm);
    }

    @POST
    public TemplateInstance post(@Valid @BeanParam EnquiryForm enquiryForm) {
        Set<ConstraintViolation<EnquiryForm>> result = validator.validate(enquiryForm);
        if (!result.isEmpty()) {
            for (ConstraintViolation<EnquiryForm> v : result) {
                logger.info(String.format("'%s': %s", v.getPropertyPath(), v.getMessage()));
            }
            sleepRandomly();
            return enquire(enquiryForm);
        }
        Enquiry enquiry = new Enquiry();
        enquiryForm.writeTo(enquiry);
        enquiryService.saveEnquiry(enquiry);
        sleepRandomly();
        return thanksView.data("actionText", "enquiring");
    }

    private void sleepRandomly() {
        try {
            Random r = new Random();
            int i = r.nextInt(999);
            Thread.sleep(i);
        }
        catch(Exception e){
            logger.warn(e);
        }
    }
}
