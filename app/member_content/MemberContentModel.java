package member_content;

import content.ContentModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;


@Entity(value = "member_content", noClassnameStored = true)
@Getter
@Setter
public class MemberContentModel extends ContentModel {

    private String memberContent;

    private ObjectId memberId;

    public enum Fields {memberId, memberContent}
}
