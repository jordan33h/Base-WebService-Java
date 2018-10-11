package core.verifier;

import core.Builder;
import core.Reflex;
import core.constant.IHTTP;
import spark.Request;
import spark.Response;

public interface RequestVerifier extends Verifier
{
    default <T extends core.Request> T object (Request request, Response response, Class<T> clazz)
    {
        body(request, response);
        T req = Reflex.inflate(request.body(), clazz);

        if (req == null || ! req.isValid())
        {
            response.body(Builder.Default.BadRequest("Invalid Request Body.", 1002));
            response.status(IHTTP.BAD_REQUEST);
        }

        return req;
    }

    default boolean body (Request request, Response response)
    {
        if (! string(request.body()))
        {
            response.body(Builder.Default.BadRequest("Request body cannot be null or empty.", 1001));
            response.status(IHTTP.BAD_REQUEST);
        }

        return true;
    }
}
