package supercars.controllers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.api.ResourcePath;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.Form;
import supercars.entities.Car;
import supercars.entities.Manufacturer;
import supercars.forms.CarForm;
import supercars.services.CarService;
import supercars.services.ManufacturerService;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

@Path("/sell")
public class SellController {
    private static final Logger logger = Logger.getLogger(SellController.class);
    private final ManufacturerService manufacturerService;
    private final CarService carService;
    private final Validator validator;
    private final Template sellView;
    private final Template thanksView;

    @Inject
    public SellController(ManufacturerService manufacturerService,
                          CarService carService,
                          Validator validator,
                          @ResourcePath("sell") Template sellView,
                          @ResourcePath("thanks") Template thanksView) {
        this.manufacturerService = manufacturerService;
        this.carService = carService;
        this.validator = validator;
        this.sellView = sellView;
        this.thanksView = thanksView;
    }

    @GET
    public TemplateInstance get() {
        CarForm carForm = new CarForm();
        return sell(carForm);
    }

    private TemplateInstance sell(CarForm carForm) {
        Collection<Manufacturer> manufacturers = manufacturerService.getManufacturers();
        sleepRandomly();
        return sellView
                .data("manufacturers", manufacturers)
                .data("carForm", carForm);
    }

    @POST
    public  TemplateInstance post(@Form CarForm carForm) {
        Set<ConstraintViolation<CarForm>> result = validator.validate(carForm);
        if (!result.isEmpty()) {
            for (ConstraintViolation<CarForm> v : result) {
                logger.info(String.format("'%s': %s", v.getPropertyPath(), v.getMessage()));
            }
            return sell(carForm);
        }
        Car car = new Car();
        carForm.writeTo(car);
        carService.saveCar(car);
        sleepRandomly();
        return thanksView.data("actionText", "selling");
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
