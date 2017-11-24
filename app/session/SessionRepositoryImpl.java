package session;


import global.common.BaseRepository;
import global.configuration.db.mongodb.MongoDBConnection;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionRepositoryImpl extends BaseRepository<SessionModel> implements SessionRepository {

    @Inject
    public SessionRepositoryImpl(MongoDBConnection databaseConnection) {
        super(SessionModel.class, databaseConnection);
    }

    @Override
    public SessionModel getSession(String authToken) {
        return query()
                .field(SessionModel.Fields.authToken.name())
                .equal(authToken)
                .get();
    }

    @Override
    public SessionModel createSession(SessionModel newUserSession) {
        create(newUserSession);
        return newUserSession;
    }

    @Override
    public boolean deleteSession(ObjectId sessionId) {
        return delete(sessionId);
    }
}
