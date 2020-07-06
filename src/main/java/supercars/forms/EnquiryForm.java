package supercars.forms;

import lombok.Getter;
import lombok.Setter;
import supercars.entities.Enquiry;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EnquiryForm {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private String comment;
    private Integer cid; // nullable
    private String carName;

    public void readFrom(Enquiry entity) {
        name = entity.getName();
        email = entity.getEmail();
        comment = entity.getComment();
        cid = entity.getCarId();
    }

    public void writeTo(Enquiry entity) {
        entity.setName(name);
        entity.setEmail(email);
        entity.setComment(comment);
        entity.setCarId(cid);
    }
}
