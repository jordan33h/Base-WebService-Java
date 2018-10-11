package core;

public abstract class Model
{
    public abstract boolean valid ();

    public String key ()
    {
        return this.getClass().getSimpleName().toLowerCase();
    }

    public String name ()
    {
        return this.getClass().getSimpleName();
    }
}
