package member_content;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import play.data.validation.Constraints;

@Getter
@Setter
public class MemberContentRequestForm {

    public String id;

    @Constraints.Required
    public ObjectId contentId;

    public String memberContent;

    @Constraints.Required
    public ObjectId memberId;

}
