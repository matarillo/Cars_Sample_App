package supercars.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@RequiredArgsConstructor
@Table("MANUFACTURERS")
public class Manufacturer {
    @Id
    private int manufacturerId;
    private String name;
    private String web;
    private String email;
    private String logo;
    @Transient
    private String country;
    @Transient
    private int engineId;
}
