package core;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface ReflexSQL
{
    default void debug ()
    {
        System.out.println("My Fields Size: " + this.getClass().getDeclaredFields().length);

        for (Field field : this.getClass().getDeclaredFields())
        {
            try
            {
                field.setAccessible(true);
                System.out.println(field.getType().getName() + " - " + field.getName() + " : " + field.get(this));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    default boolean shouldSkip (String field) { return false; }

    default List<String> getKeys ()
    {
        Field[] fields = this.getClass().getDeclaredFields();
        List<String> keys = new ArrayList<>();

        for (Field field : fields)
            if (! shouldSkip(field.getName()))
                keys.add(field.getName());

        return keys;
    }

    default Object[] getValues ()
    {
        List<String> keys = getKeys();
        if (keys != null && keys.size() > 0)
        {
            Object[] values = new Object[keys.size()];

            for (int i = 0; i < keys.size(); ++ i)
            {
                for (Field field : this.getClass().getDeclaredFields())
                {
                    if (keys.get(i).equals(field.getName()))
                    {
                        try
                        {
                            field.setAccessible(true);
                            Object value = field.get(this);

                            if (value != null)
                            {
                                if (field.getType().getName().equals("java.lang.String") && ! value.equals("NOW()"))
                                {
                                    values[i] = "'" + value + "'";
                                }
                                else
                                {
                                    values[i] = field.get(this);
                                }
                            }
                            else
                            {
                                values[i] = "NULL";
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return values;
        }
        else
        {
            if (keys == null)
                throw new IllegalStateException("Key (String[]) cannot be null !");
            else
                throw new IllegalStateException("Key (String[]) are you confirm it is ZERO length ?");
        }
    }

    default String getInsertStatement ()
    {
        return String.format("INSERT INTO %s(`%s`) VALUES (%s);", getTableName(), StringUtils.join(getKeys(), "`, `"), StringUtils.join(getValues(), ", "));
    }

    String getTableName ();
}
