package core.constant;

import org.apache.commons.lang3.StringUtils;

public interface IHeader
{
    //https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/If-Match

    String KEY_AUTHORIZATION = "Authorization";
    String KEY_ACCESS_TOKEN = "Access-Token";
    String KEY_ACCEPT_LANGUAGE = "Accept-Language";
    String KEY_CONTENT_TYPE = "Content-Type";
    String KEY_SET_COOKIE = "Set-Cookie";

    String VALUE_APPLICATION_JSON = "application/json";

    String KEY_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    String VALUE_ALLOW_HEADERS = StringUtils.join(
        KEY_AUTHORIZATION,
        KEY_ACCESS_TOKEN,
        KEY_ACCEPT_LANGUAGE,
        ",");
}