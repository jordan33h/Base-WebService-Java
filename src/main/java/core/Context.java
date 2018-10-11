package core;

import com.google.common.io.Resources;
import core.component.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class Context
{
    private static Config CONFIG;

    public static Config getConfig ()
    {
        return CONFIG;
    }

    public static void setConfig (Config config)
    {
        Context.CONFIG = config;
    }

    public static class Resource
    {
        public static String getString (String fileName, Charset charset)
        {
            try
            {
                return new String(getBytes(fileName), charset);
            }
            catch (NullPointerException ex)
            {
                ex.printStackTrace();
                return null;
            }
        }

        public static byte[] getBytes (String fileName)
        {
            try
            {
                return Resources.toByteArray(Resources.getResource(fileName));
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                return null;
            }
        }
    }
}