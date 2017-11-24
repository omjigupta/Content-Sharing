package global.common;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

@Getter
@Setter
public class BaseModel {
    @Id
    private ObjectId id;
    private long updatedAt, createdAt;


    public enum Fields {updatedAt, createdAt, id}
}
