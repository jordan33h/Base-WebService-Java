package core;

public class Status
{
    private String message;
    private int code;

    public Status (int code, String message)
    {
        this.message = message;
        this.code = code;
    }
}