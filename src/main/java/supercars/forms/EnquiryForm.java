package supercars.forms;

import lombok.Getter;
import lombok.Setter;
import supercars.entities.Enquiry;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.FormParam;

@Getter
@Setter
public class EnquiryForm {
    @NotBlank
    @FormParam("name")
    private String name;
    @NotBlank
    @FormParam("email")
    private String email;
    @FormParam("comment")
    private String comment;
    @FormParam("cid")
    private String cid; // nullable
    // private Integer cid; // nullable
    @FormParam("carName")
    private String carName;

    public void readFrom(Enquiry entity) {
        name = entity.getName();
        email = entity.getEmail();
        comment = entity.getComment();
        cid = fromInteger(entity.getCarId());
    }

    public void writeTo(Enquiry entity) {
        entity.setName(name);
        entity.setEmail(email);
        entity.setComment(comment);
        entity.setCarId(toInteger(cid));
    }

    private String fromInteger(Integer value) {
        if (value == null) {
            return null;
        } else {
            return value.toString();
        }
    }

    private Integer toInteger(String value) {
        if (value == null) {
            return  null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
