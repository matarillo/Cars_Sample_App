package supercars.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.jboss.logging.Logger;
import supercars.entities.Manufacturer;
import supercars.services.ManufacturerService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Random;

@Path("/supercars")
public class InventoryController {
    private static final Logger logger = Logger.getLogger(InventoryController.class);
    private final ManufacturerService manufacturerService;
    private final Template inventoryView;

    @Inject
    public InventoryController(ManufacturerService manufacturerService,
                               @ResourcePath("inventory") Template inventoryView) {
        this.manufacturerService = manufacturerService;
        this.inventoryView = inventoryView;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        Collection<Manufacturer> manufacturers = manufacturerService.getManufacturers();
        sleepRandomly();
        return inventoryView.data("manufacturers", manufacturers);
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
