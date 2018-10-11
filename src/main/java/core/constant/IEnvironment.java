package core.constant;

public interface IEnvironment
{
    String LOCAL = "LOCAL",
        DEVELOPMENT = "DEVELOPMENT",
        STAGING = "STAGING",
        LIVE = "LIVE",
        DOCKER = "DOCKER";

    interface Account
    {
        String STAGING = "STAGING",
            LOCAL = "LOCAL",
            DEVELOPMENT = "DEVELOPMENT",
            LIVE = "LIVE",
            TEST = "TEST";
    }
}
