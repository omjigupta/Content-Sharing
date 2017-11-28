package global.authentication;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import session.SessionService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static global.utils.JwtUtility.parseToken;

public class SecuredAction extends Action.Simple {

    @Inject
    public SessionService sessionService;

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        Optional<String> token = ctx.request().getHeaders().get("x-session-token");
            sessionService.isSessionExists(parseToken(token.get()).getId());
            return delegate.call(ctx);

    }

}
