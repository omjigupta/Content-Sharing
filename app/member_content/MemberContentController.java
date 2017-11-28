package member_content;

import global.authentication.SecuredAction;
import global.common.BaseController;
import global.exceptions.CustomException;
import org.bson.types.ObjectId;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Result;
import play.mvc.With;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MemberContentController extends BaseController {

    private final MemberContentService memberContentService;

    @Inject
    public MemberContentController(MemberContentService memberContentService) {
        this.memberContentService = memberContentService;
    }

    @With(SecuredAction.class)
    public Result updateMemberContent(String memberContentId) {
        try {
            final Form<MemberContentRequestForm> memberContentRequestForm = formFactory.form(MemberContentRequestForm.class).bindFromRequest();
            if (memberContentRequestForm.hasErrors()) {
                return failure(buildValidationErrorMessage(memberContentRequestForm.allErrors()));
            }

            if (!ObjectId.isValid(memberContentId)) {
                return failure("Invalid Member ID type");
            }

            final MemberContentRequestForm memberContentForm = memberContentRequestForm.get();

            final MemberContentModel memberContent = this.memberContentService.updateMemberContent(new ObjectId(memberContentId), memberContentForm);
            return memberContent != null ? success("successfully updated Member Content") : failure("failed to update Member Content");
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }

    @With(SecuredAction.class)
    public Result deleteMemberContent(String memberContentId) {
        try {
            if (!ObjectId.isValid(memberContentId)) {
                return failure("Invalid Content ID type");
            }
            final boolean status = memberContentService.deleteMemberContent(new ObjectId(memberContentId));

            return status ? success("successfully deleted Member Content") : failure("failed to delete Member Content");
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }

    @With(SecuredAction.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result assignContentToMember() {
        try {
            final Form<MemberContentRequestForm> memberContentRequestForm = formFactory.form(MemberContentRequestForm.class).bindFromRequest();
            if (memberContentRequestForm.hasErrors()) {
                return failure(buildValidationErrorMessage(memberContentRequestForm.allErrors()));
            }

            final MemberContentRequestForm memberForm = memberContentRequestForm.get();
            final boolean done = this.memberContentService.assignContentToMember(memberForm);
            return done ? success("successfully assigned member to content ") : failure("failed to assign member");
        } catch (CustomException e) {
            e.printStackTrace();
            return failure(e.getMessage());
        }

    }


}
