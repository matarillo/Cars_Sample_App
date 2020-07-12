package supercars.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/insurance")
public class InsuranceController {
    private static final Logger logger = Logger.getLogger(InsuranceController.class);
    private final Template insuranceView;

    @Inject
    public InsuranceController(@ResourcePath("insurance") Template insuranceView) {
        this.insuranceView = insuranceView;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        sleepRandomly();
        return insuranceView.instance();
    }

    private void sleepRandomly() {
        try {
            Random r = new Random();
            int i = r.nextInt(500);
            Thread.sleep(i);
        } catch (Exception e) {
            logger.warn(e);
        }
    }
}
