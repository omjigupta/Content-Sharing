package content;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import play.data.validation.Constraints;

@Getter
@Setter
public class ContentRequestForm {

    public String id;

    @Constraints.Required
    public String title;

    @Constraints.Required
    public String content;

    public ObjectId userId;
}
