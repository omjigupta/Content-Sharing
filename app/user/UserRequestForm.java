package user;

import lombok.Getter;
import lombok.Setter;
import play.data.validation.Constraints;

@Getter
@Setter
public class UserRequestForm {

    public String id;

    @Constraints.MaxLength(256)
    @Constraints.Required
    @Constraints.Email
    public String name;     //name is email

    @Constraints.MinLength(4)
    @Constraints.MaxLength(256)
    @Constraints.Required
    public String password;

}
