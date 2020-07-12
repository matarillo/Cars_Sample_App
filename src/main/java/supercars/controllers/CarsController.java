package supercars.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.jboss.logging.Logger;
import supercars.entities.Car;
import supercars.entities.Manufacturer;
import supercars.services.CarService;
import supercars.services.ManufacturerService;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.*;

@Path("/cars")
public class CarsController {
    private static final Logger logger = Logger.getLogger(CarsController.class);
    private final ManufacturerService manufacturerService;
    private final CarService carService;
    private final Template searchView;

    public CarsController(ManufacturerService manufacturerService,
                          CarService carService,
                          @ResourcePath("search") Template searchView) {
        this.manufacturerService = manufacturerService;
        this.carService = carService;
        this.searchView = searchView;
    }

    @GET
    public TemplateInstance get(@QueryParam("query") String query,
                                @QueryParam("mid") String manuId) {
        Map<String, Object> data = new HashMap<>();
        if (query == null) {
        } else if (query.equals("manu")) {
            getCarsByManufacturer(manuId, data);
        }
        return search(data);
    }

    public void getCarsByManufacturer(String manuIdParam, Map<String, Object> data) {
        Optional<Integer> manuId = tryParseInt(manuIdParam);
        if (manuId.isPresent()) {
            Optional<Manufacturer> manufacturer = manufacturerService.getManufacturer(manuId.get());
            if (manufacturer.isPresent()) {
                Collection<Car> cars = carService.getCarsByManufacturer(manuId.get());
                data.put("manufacturer", manufacturer.get());
                data.put("cars", cars);
                return;
            }
        }
        throw new NotFoundException("Unable to find resource");
    }

    private TemplateInstance search(Map<String, Object> data) {
        TemplateInstance instance = searchView.instance();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            instance.data(entry.getKey(), entry.getValue());
        }
        sleepRandomly();
        return instance;
    }

    private Optional<Integer> tryParseInt(String value) {
        try {
            int i = Integer.parseInt(value);
            return Optional.of(i);
        } catch (Exception ex) {
            return Optional.empty();
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
