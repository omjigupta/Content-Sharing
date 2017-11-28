package member_content;

import global.common.BaseController;

import javax.inject.Singleton;

@Singleton
public class MemberContentController extends BaseController {

    private final MemberContentService memberContentService;

    public MemberContentController(MemberContentService memberContentService) {
        this.memberContentService = memberContentService;
    }


}
