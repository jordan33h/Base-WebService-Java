package core;

import java.util.HashMap;
import java.util.Map;

public class Data
{
    private Map<String, Object> data;

    private Data ()
    {
        data = new HashMap<>();
    }

    public static Data build ()
    {
        return new Data();
    }

    public Data add (String key, Object value)
    {
        data.put(key, value);
        return this;
    }

    public Data add (Model... objects)
    {
        for (Model object : objects) data.put(object.key(), object);
        return this;
    }

    public Data remove (String key)
    {
        data.remove(key);
        return this;
    }

    public Data clear ()
    {
        data.clear();
        return this;
    }

    public Data replace (String key, String value)
    {
        data.replace(key, value);
        return this;
    }

    public Map<String, Object> getData ()
    {
        return data;
    }

    public Respond toRespond ()
    {
        return Builder.Build(data);
    }

    @Override public String toString ()
    {
        return Builder.Build(data).flat();
    }
}