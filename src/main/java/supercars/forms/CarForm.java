package supercars.forms;

import lombok.Getter;
import lombok.Setter;
import supercars.entities.Car;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.ws.rs.FormParam;

@Getter
@Setter
public class CarForm {
    @FormParam("carId")
    private Integer carId; // nullable
    @NotBlank
    @FormParam("name")
    private String name;
    @NotBlank
    @FormParam("model")
    private String model;
    @FormParam("manufacturer")
    private int manufacturer;
    @FormParam("colour")
    private String colour;
    @PositiveOrZero
    @FormParam("year")
    private int year;
    @PositiveOrZero
    @FormParam("price")
    private int price;
    @FormParam("summary")
    private String summary;
    @FormParam("description")
    private String description;
    //@Transient
    private int wheelSize;
    //@Transient
    private int tyreSize;
    //@Transient
    private boolean isManual;

    public void writeTo(Car entity) {
        entity.setName(name);
        entity.setModel(model);
        entity.setDescription(description);
        entity.setManufacturerId(manufacturer);
        entity.setColour(colour);
        entity.setYear(year);
        entity.setPrice((double)price);
        entity.setSummary(summary);
        entity.setPhoto("0"); // no photo
        entity.setWheelSize( wheelSize);
        entity.setTyreSize(tyreSize);
        entity.setManual(isManual);
    }
}
