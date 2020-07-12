package supercars.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/accessories")
public class AccessoriesController {
    private static final Logger logger = Logger.getLogger(AccessoriesController.class);
    private final Template amgView;
    private final Template alpinaView;
    private final Template gembellaView;
    private final Template mazdaspeedView;
    private final Template rufView;

    @Inject
    public AccessoriesController(@ResourcePath("accessories/amg") Template amgView,
                                 @ResourcePath("accessories/alpina") Template alpinaView,
                                 @ResourcePath("accessories/gembella") Template gembellaView,
                                 @ResourcePath("accessories/mazdaspeed") Template mazdaspeedView,
                                 @ResourcePath("accessories/ruf") Template rufView) {
        this.amgView = amgView;
        this.alpinaView = alpinaView;
        this.gembellaView = gembellaView;
        this.mazdaspeedView = mazdaspeedView;
        this.rufView = rufView;
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@PathParam("name") String name) {
        if (name != null) {
            switch (name) {
                case "amg":
                    sleepRandomly();
                    return amgView.instance();
                case "alpina":
                    sleepRandomly();
                    return alpinaView.instance();
                case "gembella":
                    sleepRandomly();
                    return gembellaView.instance();
                case "mazdaspeed":
                    sleepRandomly();
                    return mazdaspeedView.instance();
                case "ruf":
                    sleepRandomly();
                    return rufView.instance();
                default:
                    break;
            }
        }
        throw new NotFoundException("Unable to find resource");
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
