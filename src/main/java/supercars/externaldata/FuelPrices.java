package supercars.externaldata;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class FuelPrices {
    private double cng;
    private double diesel;
    private double e85;
    private double electric;
    private double lpg;
    private double midgrade;
    private double premium;
    private double regular;
}
