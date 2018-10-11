package core.verifier;

public interface Verifier
{
    default boolean string (String str)
    {
        return strings(str);
    }

    default String string (String str, String defaultStr)
    {
        return strings(str) ? str : defaultStr;
    }

    default boolean strings (String... strings)
    {
        for (String s : strings)
            if (s == null || s.trim().isEmpty())
                return false;

        return true;
    }

    default boolean array (Object[] objects)
    {
        return objects != null && objects.length > 0;
    }

    default boolean arrays (Object... objects)
    {
        if (! array(objects))
            return false;

        for (Object obj : objects)
            if (obj == null)
                return false;

        return true;
    }
}
