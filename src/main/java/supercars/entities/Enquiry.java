package supercars.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@RequiredArgsConstructor
@Table("enquiries")
public class Enquiry {
    @Id
    private Integer enquiryId; // null before insert
    private String name;
    private String email;
    private String comment;
    private Integer carId; // nullable
    private Integer dummy; // nullable
}
