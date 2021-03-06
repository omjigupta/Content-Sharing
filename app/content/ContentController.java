package content;

import com.google.common.collect.ImmutableMap;
import global.common.BaseController;
import global.exceptions.CustomException;
import org.bson.types.ObjectId;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ContentController extends BaseController {

    private final ContentService contentService;

    @Inject
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }


    @BodyParser.Of(BodyParser.Json.class)
    public Result createContent() {
        if(isSessionValid()) {
            try {
                final Form<ContentRequestForm> contentModelForm = formFactory.form(ContentRequestForm.class).bindFromRequest();
                if (contentModelForm.hasErrors()) {
                    return failure(buildValidationErrorMessage(contentModelForm.allErrors()));
                }

                final ContentRequestForm contentForm = contentModelForm.get();
                final ContentModel content = this.contentService.createContent(contentForm);
                return content != null ? success("successfully created content with ID: " + content.getId()) : failure("failed to create content");
            } catch (CustomException e) {
                e.printStackTrace();
                return failure(e.getMessage());
            }
        }
        else {
            return failure("Invalid Session");
        }

    }

    public Result getContent(String contentId) {
        if (isSessionValid()) {
            try {
                if (!ObjectId.isValid(contentId)) {
                    return failure("Invalid Content ID type");
                }
                ContentModel content = contentService.getContent(new ObjectId(contentId));
                return content != null ? success(ImmutableMap.of("content", content)) : failure("Invalid Content ID");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        }
        else {
            return failure("Invalid Session");
        }
    }

    public Result getContentByUser(String userId) {
        if (isSessionValid()) {
            try {
                if (!ObjectId.isValid(userId)) {
                    return failure("Invalid User ID type");
                }
                List<ContentModel> content = contentService.getContentByUser(new ObjectId(userId));
                return content != null ? success(ImmutableMap.of("content", content)) : failure("Failed to get Content");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        }
        else {
            return failure("Invalid Session");
        }
    }


    public Result updateContent(String contentIdStr) {
        if (isSessionValid()) {
            try {
                final Form<ContentRequestForm> contentModelForm = formFactory.form(ContentRequestForm.class).bindFromRequest();
                if (contentModelForm.hasErrors()) {
                    return failure(buildValidationErrorMessage(contentModelForm.allErrors()));
                }

                if (!ObjectId.isValid(contentIdStr)) {
                    return failure("Invalid content ID type");
                }

                final ContentRequestForm contentForm = contentModelForm.get();
                final ContentModel content = this.contentService.updateContent(new ObjectId(contentIdStr), contentForm);
                return content != null ? success("successfully updated content") : failure("failed to update content");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        }
        else {
            return failure("Invalid Session");
        }
    }

    public Result deleteContent(String contentIdStr) {
        if (isSessionValid()) {
            try {
                if (!ObjectId.isValid(contentIdStr)) {
                    return failure("Invalid Content ID type");
                }
                final boolean status = contentService.deleteContent(new ObjectId(contentIdStr));
                return status ? success("successfully deleted content") : failure("failed to delete content");
            } catch (CustomException e) {
                return failure(e.getMessage());
            }
        }
        else {
            return failure("Invalid Session");
        }
    }

    public Result getAllContent() {
        try {
            return success(ImmutableMap.of("contents", contentService.getAllContent()));
        } catch (CustomException e) {
            return failure(e.getMessage());
        }
    }

}
