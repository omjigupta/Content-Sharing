package user;

import global.common.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

@Entity(value = "users",noClassnameStored = true)
@Getter
@Setter
public class UserModel extends BaseModel {
    private String emailAddress, password;

    @Transient
    private String authToken;

    public enum Fields {password, emailAddress}


    //public enum Fields {password, authToken, name}
}
