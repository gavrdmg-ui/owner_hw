package config;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources({"classpath:${env}.properties", "classpath:local.properties"})
public interface WebDriverConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("https://www.google.com")
    String getBaseUrl();

    @Key("browser")
    @DefaultValue("CHROME")
    Browser getBrowser();

    @Key("browserVersion")
    @DefaultValue("latest")
    String getBrowserVersion();

    @Key("remote")
    @DefaultValue("local")
    Boolean isRemote();

    @Key("remoteURL")
    @DefaultValue("http://localhost:4444/wd/hub")
    URL getRemoteURL();
}