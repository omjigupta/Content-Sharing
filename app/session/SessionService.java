package session;

import global.exceptions.CustomException;
import global.utils.HashUtility;
import org.bson.types.ObjectId;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class SessionService {

    private final SessionRepository repository;

    @Inject
    public SessionService(final SessionRepository repository) {
        this.repository = repository;
    }

    public boolean deleteSession(String sessionToken) {
        final SessionModel session = this.getSession(sessionToken);
        if (session == null) {
            throw new CustomException("No user exists for given session Token");
        }

        return repository.deleteSession(session.getId());
    }

    public SessionModel getSession(String sessionToken) {
        return repository.getSession(sessionToken);
    }

    public String generateSession() {
        return UUID.randomUUID().toString();
    }

    public boolean assignSessionToUser(ObjectId userId, String session) {
        try {
            final SessionModel sessionModel = new SessionModel();

            sessionModel.setUserID(userId);
            sessionModel.setAuthToken(session);
            sessionModel.setCreatedAt(HashUtility.currentEpoch());

            repository.createSession(sessionModel);
            return true;
        }catch (Exception e){
            Logger.error("failed to assign session to user", e);
            return false;
        }
    }

    public boolean isSessionExists(String sessionToken) {
        return this.repository.getSession(sessionToken) != null;
    }
}
