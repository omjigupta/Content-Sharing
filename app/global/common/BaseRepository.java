package global.common;

import com.mongodb.WriteResult;
import global.configuration.db.mongodb.MongoDBConnection;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import play.Logger;


public abstract class BaseRepository<T extends BaseModel> {

    private Class<T> modelClass;
    @Getter
    private Datastore db;
    private Logger.ALogger logger = Logger.of(BaseRepository.class);

    public BaseRepository(Class<T> modelClass, MongoDBConnection databaseConnection) {
        this.modelClass = modelClass;
        db = databaseConnection.getDataStore();
    }

    public Query<T> query() {
        return this.db.createQuery(modelClass);
    }

    public void create(T model) {
        final Key<T> result = db.save(model);
        logger.debug("Created model with ID: {}",result.getId());
    }

    public void update(T model) {
        final Key<T> result = db.save(model);
        logger.debug("Updated model with ID: {}",result.getId());
    }

    public void create(Iterable<T> models) {
        final Iterable<Key<T>> result = db.save(models);
        result.iterator().forEachRemaining(res -> logger.debug("Updated model with ID: {}",res.getId()));
    }

    public void delete(T model) {
        final WriteResult result = db.delete(model);
        logger.debug("Deleted model with ID: {} with deletion status: {}",model.getId(),result.wasAcknowledged());
    }

    public boolean delete(ObjectId id) {
        final WriteResult result = db.delete(modelClass, id);
        logger.debug("Deleted model with ID: {} with deletion status: {}",id,result.wasAcknowledged());
        return result.wasAcknowledged();
    }

}
