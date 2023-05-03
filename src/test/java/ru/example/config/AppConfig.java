package ru.example.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:${TEST_ENV}.properties",
        "classpath:stage.properties"})
public interface AppConfig extends Config {
    @Key("test.env")
    String stand();

    @Key("uri")
    String uri();

    @Key("user.login")
    String userLogin();

    @Key("user.password")
    String userPassword();
}
