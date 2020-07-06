package supercars.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class LeakForm {
    @PositiveOrZero
    private int number; // nullable
    @PositiveOrZero
    private int size; // nullable
}
