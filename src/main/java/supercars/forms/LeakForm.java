package supercars.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;
import javax.ws.rs.FormParam;

@Getter
@Setter
public class LeakForm {
    @FormParam("number")
    @PositiveOrZero
    private int number;
    @FormParam("size")
    @PositiveOrZero
    private int size;
}
