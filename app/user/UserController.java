package user;

import com.google.common.collect.ImmutableMap;
import global.common.BaseController;
import global.exceptions.CustomException;
import global.utils.JwtUtility;
import org.bson.types.ObjectId;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Result;
import session.SessionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public final class UserController extends BaseController {

    private final UserService userService;

    private final SessionService sessionService;

    @Inject
    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public Result getUser(String userIdStr) {
        if (isSessionValid()) {
            try {
                if (!ObjectId.isValid(userIdStr)) {
                    return failure("Invalid User ID type");
                }
                UserModel user = userService.getUser(new ObjectId(userIdStr));
                return user != null ? success(ImmutableMap.of("user", user)) : failure("Invalid User ID");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Invalid Session");
        }

    }

    @BodyParser.Of(BodyParser.Json.class)
    public  Result loginUser(){
        final Form<UserRequestForm> userRequestForm = formFactory.form(UserRequestForm.class).bindFromRequest();
        if(userRequestForm.hasErrors()){
            return  failure(buildValidationErrorMessage(userRequestForm.allErrors()));
        }
        final UserRequestForm mFrom = userRequestForm.get();
        Optional<UserModel> mModelOpt = this.userService.loginUser(mFrom);
        if(mModelOpt.isPresent()){
            UserModel userModel = mModelOpt.get();
            String session = sessionService.generateSession();
            boolean status = sessionService.assignSessionToUser(userModel.getId(), session);

            Map<String, Object> claim = new HashMap<String,Object>();
            claim.put(session, userModel.getId());
            String jwtToken = JwtUtility.createJWT(session, userModel.getEmailAddress(),"Archon",claim);

            return status ? success("successfully login", ImmutableMap.of("x-session-token",jwtToken)) : failure("failed to login");
        }else{
            return failure("Invalid Login credentials");
        }

    }

    public Result logoutUser(String sessionToken) {

        boolean sModel = this.sessionService.deleteSession(sessionToken);
        return  sModel ? success("You've been successfully logged out") : failure("not logout");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result createUser() {
        try {
            final Form<UserRequestForm> userModelForm = formFactory.form(UserRequestForm.class).bindFromRequest();
            if (userModelForm.hasErrors()) {
                return failure(buildValidationErrorMessage(userModelForm.allErrors()));
            }

            final UserRequestForm userForm = userModelForm.get();
            final UserModel user = this.userService.createUser(userForm);
            return user != null ? success("successfully created user with ID: " + user.getId()) : failure("failed to create user");
        } catch (CustomException e) {
            e.printStackTrace();
            return failure(e.getMessage());
        }
    }

    public Result updateUser(String userIdStr) {
        if (isSessionValid()) {
            try {
                final Form<UserRequestForm> userModelForm = formFactory.form(UserRequestForm.class).bindFromRequest();
                if (userModelForm.hasErrors()) {
                    return failure(buildValidationErrorMessage(userModelForm.allErrors()));
                }

                if (!ObjectId.isValid(userIdStr)) {
                    return failure("Invalid User ID type");
                }

                final UserRequestForm userForm = userModelForm.get();
                final UserModel user = this.userService.updateUser(new ObjectId(userIdStr), userForm);
                return user != null ? success("successfully updated user") : failure("failed to update user");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        } else {
            return failure("Invalid Session");
        }
    }

    public Result deleteUser(String userIdStr) {
        if (isSessionValid()) {
            try {
                if (!ObjectId.isValid(userIdStr)) {
                    return failure("Invalid User ID type");
                }
                final boolean status = userService.deleteUser(new ObjectId(userIdStr));
                return status ? success("successfully deleted user") : failure("failed to delete user");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        }
        else {
            return failure("Invalid Session");
        }
    }
    public Result getAllUsers() {
        if (isSessionValid()) {
            try {
                return success(ImmutableMap.of("users", userService.getAllUsers()));
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        }
        else {
            return failure("Invalid Session");
        }
    }

}
