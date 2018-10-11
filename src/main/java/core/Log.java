package core;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

public class Log
{
    private static Logger mLogger = LoggerFactory.getLogger("spike");
    private static Joiner mJoin = Joiner.on(", ").useForNull("null");

    public static void r (Request request)
    {
        mLogger.debug("");
        mLogger.debug("Context Path: " + request.contextPath());
        mLogger.debug("Servlet Path: " + request.servletPath());
        mLogger.debug("Path Info: " + request.pathInfo());
        mLogger.debug("URI: " + request.uri());
        mLogger.debug("URL: " + request.url());
        mLogger.debug(mJoin.withKeyValueSeparator(':').join(request.params()));
        mLogger.debug(request.body());
        mLogger.debug("");
    }

    public static void ex(Exception e)
    {
        mLogger.error("");
        mLogger.error("---------------------------------------------------------");
        mLogger.error("-------------------- Exception Occur --------------------");
        mLogger.error("---------------------------------------------------------");
        mLogger.error("");
        e.printStackTrace();
        mLogger.error("");
        mLogger.error("---------------------------------------------------------");
        mLogger.error("-------------------- Exception Occur --------------------");
        mLogger.error("---------------------------------------------------------");
        mLogger.error("");
    }

    public static void w(String s, Throwable object)
    {
        mLogger.warn("");
        mLogger.warn(s, object);
        mLogger.warn("");
    }

    public static void w(String s, Object object)
    {
        mLogger.warn("");
        mLogger.warn(s, object);
        mLogger.warn("");
    }

    public static void w(String... message)
    {
        mLogger.warn("");
        for(String s : message)
            mLogger.warn(s);
        mLogger.warn("");
    }

    public static void d(String s, Throwable object)
    {
        mLogger.debug("");
        mLogger.debug(s, object);
        mLogger.debug("");
    }

    public static void d(String s, Object object)
    {
        mLogger.debug("");
        mLogger.debug(s, object);
        mLogger.debug("");
    }

    public static void d(String... message)
    {
        mLogger.debug("");
        for(String s : message)
            mLogger.debug(s);
        mLogger.debug("");
    }

    public static void e(String s, Throwable object)
    {
        mLogger.error("");
        mLogger.error(s, object);
        mLogger.error("");
    }

    public static void e(String s, Object object)
    {
        mLogger.error("");
        mLogger.error(s, object);
        mLogger.error("");
    }

    public static void e(String... message)
    {
        mLogger.error("");
        for(String s : message)
            mLogger.error(s);
        mLogger.error("");
    }

    public static void i(String s, Throwable object)
    {
        mLogger.info("");
        mLogger.info(s, object);
        mLogger.info("");
    }

    public static void i(String s, Object object)
    {
        mLogger.info("");
        mLogger.info(s, object);
        mLogger.info("");
    }

    public static void i(String... message)
    {
        mLogger.info("");
        for(String s : message)
            mLogger.info(s);
        mLogger.info("");
    }
}