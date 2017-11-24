package member_content;

import global.exceptions.CustomException;
import org.bson.types.ObjectId;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class MemberContentService {

    private MemberContentRepository memberContentRepository;

    @Inject
    public MemberContentService(MemberContentRepository memberContentRepository) {
        this.memberContentRepository = memberContentRepository;
    }

    List<MemberContentModel> getMemberContentByUser (ObjectId memberId) {
        return memberContentRepository.getMemberContentByUser(memberId);
    }

    MemberContentModel getMemberContentByContentId (ObjectId contentId) {
        return memberContentRepository.getMemberContentByContentId(contentId);
    }

    public MemberContentModel updateMemberContent(ObjectId contentId, MemberContentRequestForm memberContentRequestForm) {
        final MemberContentModel memberContentModel = memberContentRepository.getMemberContentByContentId(contentId);
        if(memberContentModel == null) {
            throw new CustomException("No Content exists for given content Id");
        }

        memberContentModel.setMemberContent(memberContentRequestForm.getMemberContent());
        memberContentModel.setMemberId(memberContentRequestForm.getMemberId());

        memberContentRepository.updateMemberContent(memberContentModel);

        return memberContentModel;

    }

    boolean assignContentToMember(MemberContentRequestForm memberContentRequestForm) {
        try {
            final MemberContentModel memberContentModel = new MemberContentModel();

            memberContentModel.setMemberId(memberContentRequestForm.getMemberId());
            memberContentModel.setMemberContent(memberContentRequestForm.getMemberContent());
            memberContentRepository.assignContentToMember(memberContentModel);
            return true;
        }catch (Exception e){
            Logger.error("failed to assign content to user", e);
            return false;
        }
    }

    boolean deleteMemberContent(ObjectId memberContentId) {
        final MemberContentModel memberContentModel = memberContentRepository.getMemberContentByContentId(memberContentId);
        if (memberContentModel == null) {
            throw new CustomException("No content exists for given Member Content ID");
        }

        return memberContentRepository.deleteMemberContent(memberContentId);
    }
}
