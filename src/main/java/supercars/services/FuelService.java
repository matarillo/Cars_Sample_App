package supercars.services;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import supercars.externaldata.FuelPrices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface FuelService {
    @GET
    @Path("/fuelprices")
    @Produces(MediaType.APPLICATION_XML)
    FuelPrices getFuelPrices();
}
