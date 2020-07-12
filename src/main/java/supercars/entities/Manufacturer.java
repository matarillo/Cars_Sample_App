package supercars.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
//@Table("MANUFACTURERS")
public class Manufacturer {
    //@Id
    private int manufacturerId;
    private String name;
    private String web;
    private String email;
    private String logo;
    //@Transient
    private String country;
    //@Transient
    private int engineId;
}
