package supercars.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import supercars.externaldata.FuelPrices;
import supercars.services.FuelService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/home")
public class HomeController {
    private static final Logger logger = Logger.getLogger(HomeController.class);
    private final FuelService fuelService;
    private final Template homeView;

    @Inject
    public HomeController(@RestClient FuelService fuelService,
                          @ResourcePath("home") Template homeView) {
        this.fuelService = fuelService;
        this.homeView = homeView;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        FuelPrices prices = fuelService.getFuelPrices();
        sleepRandomly();
        return homeView.data("prices", prices);
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
