package supercars.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@RequiredArgsConstructor
@Table("cars")
public class Car {
    @Id
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
    @Transient
    private int wheelSize;
    @Transient
    private int tyreSize;
    @Transient
    private boolean isManual;
}
