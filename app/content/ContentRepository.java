package content;

import org.bson.types.ObjectId;

import java.util.List;

public interface ContentRepository {

    ContentModel createContent(ContentModel content);

    ContentModel getContent (final ObjectId contentId);

    List<ContentModel> getAllContent();

    List<ContentModel> getContentByUser(final ObjectId userId);

    void  updateContent(ContentModel content);

    boolean deleteContent(final ObjectId contentId);

}
