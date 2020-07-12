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

@Path("/about")
public class AboutController {
    private static final Logger logger = Logger.getLogger(AboutController.class);
    private final Template aboutView;

    @Inject
    public AboutController(@ResourcePath("about") Template aboutView) {
        this.aboutView = aboutView;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        sleepRandomly();
        return aboutView.instance();
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
