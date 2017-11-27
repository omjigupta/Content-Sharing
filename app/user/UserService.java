package user;

import global.exceptions.CustomException;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static global.utils.Helper.compare;
import static global.utils.Helper.hash;

@Singleton
public class UserService {

    private final UserRepository repository;

    @Inject
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    List<UserModel> getAllUsers() {
        return repository.getAllUsers();
    }

    boolean deleteUser(ObjectId userId) {
        final UserModel user = repository.getUser(userId);
        if (user == null) {
            throw new CustomException("No user exists for given user ID");
        }

        return repository.deleteUser(userId);
    }

    public UserModel getUser(ObjectId userId) {
        return repository.getUser(userId);
    }

    public UserModel createUser(UserRequestForm userForm) {
        final UserModel newUser = new UserModel();
        newUser.setPassword(hash(userForm.getPassword()));
        newUser.setEmailAddress(userForm.getEmail());
        return repository.createUser(newUser);
    }


    public UserModel updateUser(ObjectId userId, UserRequestForm userForm) {

        final UserModel user = repository.getUser(userId);
        if (user == null) {
            throw new CustomException("No user exists for given user ID");
        }

        user.setEmailAddress(userForm.getEmail());
        user.setPassword(hash(userForm.getPassword()));

        repository.updateUser(user);

        return user;
    }

    public Optional<UserModel> loginUser(UserRequestForm mForm){
      final UserModel user = repository.getUserByEmail(mForm.getEmail());
      boolean done = compare(mForm.getPassword(),user.getPassword());

      if(done)
          return Optional.of(user);
      else
          return  Optional.empty();

    }



}
