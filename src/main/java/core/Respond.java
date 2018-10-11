package core;

import core.constant.IHTTP;
import spark.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Respond
{
    private int status;
    private String message;
    private List<Status> statusList;
    private Object data;

    public int getStatus ()
    {
        return status;
    }

    public Respond setStatus (int status)
    {
        this.status = status;
        return this;
    }

    public List<Status> getStatusList ()
    {
        if (statusList == null)
            statusList = new ArrayList<Status>();
        return statusList;
    }

    public Respond setStatusList (List<Status> statusList)
    {
        this.statusList = statusList;
        return this;
    }

    public Object getData ()
    {
        return data;
    }

    public Respond setData (Object data)
    {
        this.data = data;
        return this;
    }

    public String getMessage ()
    {
        return message;
    }

    public Respond setMessage (String message)
    {
        this.message = message;
        return this;
    }

    public String flat ()
    {
        return Reflex.flat(this);
    }

    public static class Builder
    {
        private Respond mRespond;
        private Data mData;

        private Builder ()
        {
            mRespond = new Respond();
            mData = Data.build();
        }

        public static Builder create ()
        {
            return new Builder();
        }

        public Builder status (Status... status)
        {
            Collections.addAll(mRespond.getStatusList(), status);
            return this;
        }

        public Builder global (int code, String message)
        {
            mRespond.setStatus(code).setMessage(message);
            return this;
        }

        public Builder status (int code, String message)
        {
            mRespond.getStatusList().add(new Status(code, message));
            return this;
        }

        public Builder data (String key, Object value)
        {
            mData.add(key, value);
            return this;
        }

        public Builder data (BaseModel... objects)
        {
            mData.add(objects);
            return this;
        }

        public Builder error (Response resp)
        {
            mRespond.setStatus(IHTTP.BAD_REQUEST);
            resp.status(IHTTP.BAD_REQUEST);
            return this;
        }

        public Builder error (int statusCode, Response resp)
        {
            mRespond.setStatus(statusCode);
            resp.status(statusCode);
            return this;
        }

        public String flat ()
        {
            return mRespond.flat();
        }
    }
}