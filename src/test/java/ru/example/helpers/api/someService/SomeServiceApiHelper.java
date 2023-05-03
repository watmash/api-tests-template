package ru.example.helpers.api.someService;

import io.restassured.specification.RequestSpecification;
import ru.example.helpers.api.ApiHelper;

public class SomeServiceApiHelper extends ApiHelper {

    public SomeServiceApiHelper(String uri) {
        super(uri);
    }

    public SomeServiceApiHelper(String uri, String token) {
        super(uri, token);
    }

    @Override
    public RequestSpecification getReqWithApiKey() {
        if (getToken() != null) {
            return getReq().header("Authorization", getToken());
        } else {
            return getReq();
        }
    }
}
