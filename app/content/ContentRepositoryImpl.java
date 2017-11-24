package content;

import global.common.BaseModel;
import global.common.BaseRepository;
import global.configuration.db.mongodb.MongoDBConnection;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ContentRepositoryImpl extends BaseRepository<ContentModel> implements ContentRepository {


    @Inject
    public ContentRepositoryImpl(MongoDBConnection databaseConnection) {
        super(ContentModel.class, databaseConnection);
    }

    @Override
    public ContentModel createContent(ContentModel content) {
        create(content);
        return content;
    }

    @Override
    public ContentModel getContent(ObjectId contentId) {
        return query()
                .field(BaseModel.Fields.id.name())
                .equal(contentId)
                .get();
    }

    @Override
    public List<ContentModel> getAllContent() {

        return query().asList();
    }

    @Override
    public List<ContentModel> getContentByUser(ObjectId userId) {
        return query()
                .field(ContentModel.Fields.userId.name())
                .equal(userId)
                .asList();
    }

    @Override
    public void updateContent(ContentModel content) {
         update(content);
    }

    @Override
    public boolean deleteContent(ObjectId contentId) {
        return delete(contentId);
    }
}
