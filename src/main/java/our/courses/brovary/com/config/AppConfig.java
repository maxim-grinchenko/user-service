package our.courses.brovary.com.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:application.properties")
public interface AppConfig extends Config {
    @Key("db.url")
    String url();

    @Key("db.password")
    String password();

    @Key("db.login")
    String login();

    @Key("base.security.login")
    String securityPassword();

    @Key("base.security.password")
    String securityLogin();
}