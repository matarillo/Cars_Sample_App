package supercars.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.Form;
import supercars.forms.LeakForm;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Path("/leak")
public class LeakController {
    public static final List<byte[]> leakyCollection = new LinkedList<>();
    private static final Logger logger = Logger.getLogger(LeakController.class);
    private final Validator validator;
    private final Template leakView;
    private final Template thanksView;

    @Inject
    public LeakController(Validator validator,
                          @ResourcePath("leak") Template leakView,
                          @ResourcePath("thanks") Template thanksView) {
        this.validator = validator;
        this.leakView = leakView;
        this.thanksView = thanksView;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        sleepRandomly();
        return leakView.instance();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance post(@Form LeakForm leakForm) {
        Set<ConstraintViolation<LeakForm>> result = validator.validate(leakForm);
        if (result.isEmpty()) {
            addToCollection(leakForm.getNumber(), leakForm.getSize());
        } else {
            for (ConstraintViolation<LeakForm> v : result) {
                logger.info(String.format("'%s': %s", v.getPropertyPath(), v.getMessage()));
            }
        }
        sleepRandomly();
        return thanksView.data("actionText", "selling");
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
            logger.warn(e);
        }
    }
}
