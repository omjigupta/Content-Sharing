package user;

import org.bson.types.ObjectId;

import java.util.List;


public interface UserRepository {

    UserModel getUserByName(final String name);

    List<UserModel> getAllUsers();

    boolean deleteUser(final ObjectId userId);

    void updateUser(final UserModel user);

    UserModel getUser(ObjectId userId);

    UserModel createUser(UserModel newUser);

}
