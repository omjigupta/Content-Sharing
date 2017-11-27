package user;

import org.bson.types.ObjectId;

import java.util.List;


public interface UserRepository {

    UserModel getUserByEmail(final String email);

    List<UserModel> getAllUsers();

    boolean deleteUser(final ObjectId userId);

    void updateUser(final UserModel user);

    UserModel getUser(ObjectId userId);

    UserModel createUser(UserModel newUser);

}
