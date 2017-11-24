package content;

import global.common.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "content",noClassnameStored = true)
@Getter
@Setter
public class ContentModel extends BaseModel {

    private String content;

    private String title;

    private ObjectId userId;

    public enum Fields {title, userId, content}
}
