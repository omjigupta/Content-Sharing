package content;

import global.exceptions.CustomException;
import org.bson.types.ObjectId;
import user.UserModel;
import user.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ContentService {

    private final ContentRepository repo;

    @Inject
    public ContentService(ContentRepository repo) {
        this.repo = repo;
    }


    public ContentModel createContent(ContentRequestForm contentForm) {
        final ContentModel newContent = new ContentModel();
        newContent.setTitle(contentForm.getTitle());
        newContent.setContent(contentForm.getContent());
        newContent.setUserId(contentForm.getUserId());
        return repo.createContent(newContent);
    }

    public ContentModel getContent(ObjectId contentId) {
        return repo.getContent(contentId);
    }

    List<ContentModel> getContentByUser(ObjectId userId) {
        return repo.getContentByUser(userId);
    }

    public ContentModel updateContent (ObjectId contentId, ContentRequestForm contentForm) {
        final ContentModel content = repo.getContent(contentId);
        if (content == null) {
            throw new CustomException("No Content exists for given content ID");
        }

        content.setTitle(contentForm.getTitle());
        content.setContent(contentForm.getContent());
        content.setUserId(contentForm.getUserId());

        repo.updateContent(content);

        return content;
    }

    public boolean deleteContent(ObjectId contentId) {
        final ContentModel content = repo.getContent(contentId);
        if (content == null) {
            throw new CustomException("No content exists for given content ID");
        }
        return repo.deleteContent(contentId);
    }

    List<ContentModel> getAllContent() {
        return repo.getAllContent();
    }
}
