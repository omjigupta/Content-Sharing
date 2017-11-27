package member_content;

import global.common.BaseModel;
import global.common.BaseRepository;
import global.configuration.db.mongodb.MongoDBConnection;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class MemberContentRepositoryImpl extends BaseRepository<MemberContentModel> implements MemberContentRepository {

    @Inject
    public MemberContentRepositoryImpl(MongoDBConnection databaseConnection) {
        super(MemberContentModel.class, databaseConnection);
    }

    @Override
    public MemberContentModel assignContentToMember(MemberContentModel memberContentModel) {
        create(memberContentModel);
        return memberContentModel;
    }

    @Override
    public MemberContentModel getMemberContentByContentId(ObjectId contentId) {
        return query()
                .field(BaseModel.Fields.id.name())
                .equal(contentId)
                .get();
    }

    @Override
    public boolean deleteMemberContent(ObjectId memberContentId) {
        return delete(memberContentId);
    }

    @Override
    public void updateMemberContent(MemberContentModel memberContentModel) {
        update(memberContentModel);

    }

    @Override
    public List<MemberContentModel> getMemberContentByUser(ObjectId memberId) {
        return query()
                .field(BaseModel.Fields.id.name())
                .equal(memberId)
                .asList();
    }
}
