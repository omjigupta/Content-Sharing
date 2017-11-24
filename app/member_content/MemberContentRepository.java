package member_content;

import com.google.inject.ImplementedBy;
import org.bson.types.ObjectId;

import java.util.List;

@ImplementedBy(MemberContentRepositoryImpl.class)
public interface MemberContentRepository {

    MemberContentModel assignContentToMember(MemberContentModel memberContentModel);

    MemberContentModel getMemberContentByContentId(final ObjectId contentId);

    boolean deleteMemberContent(final ObjectId memberContentId);

    void updateMemberContent (MemberContentModel memberContentModel);

    List<MemberContentModel> getMemberContentByUser(final ObjectId memberId);

}
