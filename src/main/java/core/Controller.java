package core;

import core.verifier.RequestVerifier;
import org.slf4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public abstract class Controller implements RequestVerifier
{
    public Sql2o mSQL;

    protected Controller (Sql2o sql)
    {
        mSQL = sql;
        Log.i(this.getClass().getSimpleName() + " Initialized");
    }

    public Connection getConnection ()
    {
        try (Connection conn = mSQL.open())
        {
            return conn;
        }
        catch (Exception e)
        {
            unexpected(e);
            return null;
        }
    }

    public String unexpected (Exception e)
    {
        Log.ex(e);
        return Builder.Default.Exception(e);
    }
}
