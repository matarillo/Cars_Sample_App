package supercars.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Enquiry {
    //@Id
    private Integer enquiryId; // null before insert
    private String name;
    private String email;
    private String comment;
    private Integer carId; // nullable
    private Integer dummy; // nullable

    @Override
    public Enquiry clone() {
        Enquiry newEnquiry = new Enquiry();
        newEnquiry.enquiryId = enquiryId;
        newEnquiry.name = name;
        newEnquiry.email = email;
        newEnquiry.comment = comment;
        newEnquiry.carId = carId;
        newEnquiry.dummy = dummy;
        return newEnquiry;
    }
}
