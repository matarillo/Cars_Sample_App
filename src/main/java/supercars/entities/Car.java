package supercars.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Car {
    //@Id
    private Integer carId; // null before insert
    private String name;
    private String model;
    private String description;
    private int manufacturerId;
    private String colour;
    private int year;
    private double price;
    private String summary;
    private String photo;
    //@Transient
    private int wheelSize;
    //@Transient
    private int tyreSize;
    //@Transient
    private boolean isManual;

    @Override
    public Car clone() {
        Car newCar = new Car();
        newCar.carId = carId;
        newCar.name = name;
        newCar.model = model;
        newCar.description = description;
        newCar.manufacturerId = manufacturerId;
        newCar.colour = colour;
        newCar.year = year;
        newCar.price = price;
        newCar.summary = summary;
        newCar.photo = photo;
        newCar.wheelSize = wheelSize;
        newCar.tyreSize = tyreSize;
        newCar.isManual = isManual;
        return newCar;
    }
}
