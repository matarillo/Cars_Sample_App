package supercars.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import supercars.externaldata.FuelPrices;
import supercars.logging.Logger;

@Service
public class FuelService {
    private final RestTemplate restTemplate;

    public FuelService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public FuelPrices getFuelPrices() {
        try {
            String url = "https://www.fueleconomy.gov/ws/rest/fuelprices";
            return restTemplate.getForObject(url, FuelPrices.class);
        } catch (Exception e) {
            Logger.log(e);
            return new FuelPrices();
        }
    }
}
