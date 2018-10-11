package core;

import com.google.common.base.Throwables;
import com.sun.tools.javac.util.List;
import core.constant.IHTTP;

public class Builder
{
    public static Respond NoMessage (int httpCode, boolean result)
    {
        return new Respond()
            .setStatus(httpCode);
    }

    public static Respond NoMessage (boolean result)
    {
        return NoMessage(IHTTP.OK, false);
    }

    public static Respond NoMessage ()
    {
        return NoMessage(IHTTP.OK, true);
    }

    public static Respond WithMessage (String msg, int code, int httpCode, boolean result)
    {
        Respond resp = new Respond()
            .setStatus(httpCode);

        resp.getStatusList().add(new Status(code, msg));
        return resp;
    }

    public static Respond WithMessage (String msg, int code, boolean result)
    {
        return WithMessage(msg, code, IHTTP.OK, result);
    }

    public static Respond Build (List<Status> list, int code, boolean result)
    {
        return new Respond()
            .setStatusList(list)
            .setStatus(code);
    }

    public static Respond Build (Object data)
    {
        return new Respond()
            .setStatus(IHTTP.OK)
            .setData(data);
    }

    public static class Default
    {
        public static String Exception (Exception ex)
        {
            return Builder.WithMessage("Internal Server Error :( Unexpected Exception, " +
                    ex.getMessage() + ", " +
                    Throwables.getStackTraceAsString(ex),
                9999, IHTTP.INTERNAL_SERVER_ERROR, false).flat();
        }


        public static String BadRequest (String str, int errorCode)
        {
            return WithMessage(str, errorCode, IHTTP.BAD_REQUEST, false).flat();
        }


        public static String NoResult ()
        {
            return NoMessage(false).flat();
        }

        public static String NoResult (String msg, int code)
        {
            return WithMessage(msg, code, false).flat();
        }

        public static String NoResult (List<Status> status, int code)
        {
            Respond resp = new Respond();
            resp.setStatus(IHTTP.OK);
            for (Status re : status)
                resp.getStatusList().add(re);

            return resp.flat();
        }


        public static String Success ()
        {
            return NoMessage().flat();
        }

        public static String Success (String msg)
        {
            return WithMessage(msg, IHTTP.OK, true).flat();
        }
    }
}
