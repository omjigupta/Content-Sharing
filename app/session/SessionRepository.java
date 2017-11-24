package session;

import com.google.inject.ImplementedBy;
import org.bson.types.ObjectId;

@ImplementedBy(SessionRepositoryImpl.class)
public interface SessionRepository {

    SessionModel getSession(String authToken);

    SessionModel createSession(SessionModel newUserSession);

    boolean deleteSession(ObjectId sessionId);
}
