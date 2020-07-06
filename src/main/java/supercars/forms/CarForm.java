package supercars.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import supercars.entities.Car;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class CarForm {
    private Integer carId; // nullable
    @NotBlank
    private String name;
    @NotBlank
    private String model;
    private int manufacturer;
    private String colour;
    @PositiveOrZero
    private int year;
    @PositiveOrZero
    private int price;
    private String summary;
    private String description;
    @Transient
    private int wheelSize;
    @Transient
    private int tyreSize;
    @Transient
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
