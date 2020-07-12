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
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.*;

@Path("/search")
public class SearchController {
    private static final Logger logger = Logger.getLogger(SearchController.class);
    private final ManufacturerService manufacturerService;
    private final CarService carService;
    private final Template searchView;

    public SearchController(ManufacturerService manufacturerService,
                            CarService carService,
                            @ResourcePath("search") Template searchView) {
        this.manufacturerService = manufacturerService;
        this.carService = carService;
        this.searchView = searchView;
    }

    @GET
    public TemplateInstance get(@QueryParam("query") String query,
                                @QueryParam("criteria") String criteria) {
        Map<String, Object> data = new HashMap<>();
        if (query == null) {
        } else if (query.equals("search")) {
            getCarsBySearch(criteria, data);
        }
        return search(data);
    }

    public void getCarsBySearch(String criteria, Map<String, Object> data) {
        Map<Integer, Manufacturer> manufacturers = new HashMap<>();
        Collection<Car> cars = carService.getCarsBySearch(criteria);
        for (Car car : cars) {
            Optional<Manufacturer> manufacturer = manufacturerService.getManufacturer(car.getManufacturerId());
            if (manufacturer.isPresent()) {
                manufacturers.put(car.getManufacturerId(), manufacturer.get());
            }
        }
        data.put("criteria", criteria);
        data.put("cars", cars);
        data.put("manufacturers", manufacturers);
    }

    private TemplateInstance search(Map<String, Object> data) {
        TemplateInstance instance = searchView.instance();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            instance.data(entry.getKey(), entry.getValue());
        }
        sleepRandomly();
        return instance;
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
