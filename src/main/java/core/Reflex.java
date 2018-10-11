package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Reflex
{
    private static Gson mGson = new GsonBuilder().create();

    public static String flat (Object object)
    {
        return mGson.toJson(object);
    }

    public static <T> T inflate (String jsonString, Class<T> clazz)
    {
        return mGson.fromJson(jsonString, clazz);
    }

    public static <T> List<T> list (String jsonString, Class<T> clazz)
    {
        return mGson.fromJson(jsonString, TypeToken.getParameterized(ArrayList.class, clazz).getType());
    }

    public static <T> List<T> list (String jsonString, Type type)
    {
        return mGson.fromJson(jsonString, TypeToken.getParameterized(ArrayList.class, type).getType());
    }

    public static <T extends Serializable> T clone (T object)
    {
        if (object == null)
            return null;

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bin);

            return (T) ois.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                if (oos != null)
                    oos.close();

                if (ois != null)
                    ois.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
