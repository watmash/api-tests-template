package ru.example.helpers.api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.MULTIPART;

@Getter
@Setter
public class ApiHelper {
    public ResponseSpecification resp200 = expect().statusCode(200);
    private String token;
    private String uri;

    static {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.registerParser(ContentType.TEXT.toString(), Parser.JSON);
    }

    public ApiHelper(String uri) {
        this.uri = uri;
    }

    public ApiHelper(String token, String uri) {
        this.token = token;
        this.uri = uri;
    }

    public RequestSpecification getReq() {
        RequestSpecification requestSpecification = given()
                .baseUri(uri)
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssured())
                .relaxedHTTPSValidation();
        if (System.getenv("TEST_ENV") == null) {
            requestSpecification
                    .log().all()
                    .then()
                    .log().all();
        } else {
            requestSpecification
                    .log().ifValidationFails()
                    .then()
                    .log().ifValidationFails();
        }
        return requestSpecification.request();
    }

    public RequestSpecification getReqWithApiKey() {
        if (token != null) {
            return getReq().header("Authorization", token);
        } else {
            return getReq();
        }
    }

    @Step("get {url}")
    public Response getUrl(ResponseSpecification resp, String url) {
        return given()
                .contentType(ContentType.JSON)
                .relaxedHTTPSValidation()
                .expect()
                .spec(resp)
                .when()
                .get(url);
    }

    @Step("get {endpoint}")
    public Response get(String endpoint) {
        return getReqWithApiKey()
                .expect()
                .when()
                .get(endpoint);
    }

    @Step("get {endpoint}")
    public Response get(String endpoint, Map<String, ?> queryParams, Object... pathParams) {
        return getReqWithApiKey()
                .params(queryParams)
                .expect()
                .when()
                .get(endpoint, pathParams);
    }

    @Step("get {endpoint}")
    public Response get(ResponseSpecification resp, String endpoint) {
        return getReqWithApiKey()
                .expect()
                .spec(resp)
                .when()
                .get(endpoint);
    }

    @Step("get {endpoint}")
    public Response getWithHeader(ResponseSpecification resp, String endpoint, Map<String, ?> queryParams, Map<String, ?> headers, Object... pathParams) {
        return getReqWithApiKey()
                .headers(headers)
                .params(queryParams)
                .expect()
                .spec(resp)
                .when()
                .get(endpoint, pathParams);
    }

    @Step("get {endpoint}")
    public Response getWithHeader(ResponseSpecification resp, String endpoint, Map<String, ?> headers, Object... pathParams) {
        return getReqWithApiKey()
                .headers(headers)
                .expect()
                .spec(resp)
                .when()
                .get(endpoint, pathParams);
    }

    @Step("get {endpoint}")
    public Response get(ResponseSpecification resp, String endpoint, Object... pathParams) {
        return getReqWithApiKey()
                .expect()
                .spec(resp)
                .when()
                .get(endpoint, pathParams);
    }

    @Step("get {endpoint}")
    public Response get(ResponseSpecification resp, String endpoint, Map<String, ?> queryParams, Object... pathParams) {
        return getReqWithApiKey()
                .params(queryParams)
                .expect()
                .spec(resp)
                .when()
                .get(endpoint, pathParams);
    }

    @Step("get {endpoint}")
    public Response getWithoutLogging(ResponseSpecification resp, String endpoint, Map<String, ?> queryParams, Object... pathParams) {
        return getReqWithApiKey()
                .noFilters()
                .params(queryParams)
                .expect()
                .spec(resp)
                .when()
                .get(endpoint, pathParams);
    }

    @Step("post {endpoint}")
    public Response post(String endpoint, Object body) {
        return getReqWithApiKey()
                .body(formatIfJsonObject(body))
                .expect()
                .when()
                .post(endpoint);
    }

    @Step("post {endpoint}")
    public Response post(Object body, ResponseSpecification resp, String endpoint) {
        return getReqWithApiKey()
                .body(body)
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }

    @Step("postWithoutBody {endpoint}")
    public Response postWithoutBody(ResponseSpecification resp, String endpoint) {
        return getReqWithApiKey()
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }

    @Step("post {endpoint}")
    public Response postWithoutBody(ResponseSpecification resp, String endpoint, Object... pathParams) {
        return getReqWithApiKey()
                .expect()
                .spec(resp)
                .when()
                .post(endpoint, pathParams);
    }

    @Step("post {endpoint}")
    public Response postWithoutBody(ResponseSpecification resp, String endpoint, Map<String, ?> queryParams) {
        return getReqWithApiKey()
                .queryParams(queryParams)
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }


    @Step("post {endpoint}")
    public Response post(ResponseSpecification resp, String endpoint, Object body, Object... params) {
        return getReqWithApiKey()
                .body(formatIfJsonObject(body))
                .expect()
                .spec(resp)
                .when()
                .post(endpoint, params);
    }

    @Step("post {endpoint}")
    public Response postMultipart(ResponseSpecification resp, String endpoint, Map<String, Object> multiParts) {
        RequestSpecification specification = getReqWithApiKey()
                .contentType(MULTIPART);
        for (Map.Entry<String, Object> multiPart : multiParts.entrySet()) {
            specification.multiPart(multiPart.getKey(), multiPart.getValue());
        }
        return specification
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }

    @Step("post {endpoint}")
    public Response post(String endpoint, Object body, Object... params) {
        return getReqWithApiKey()
                .body(formatIfJsonObject(body))
                .expect()
                .when()
                .post(endpoint, params);
    }

    @Step("post {endpoint}")
    public Response postWithCookies(ResponseSpecification resp, String endpoint, Map<String, String> queryParams, JSONObject body, Map<String, String> cookies, Object... params) {
        return getReqWithApiKey()
                .cookies(cookies)
                .body(formatIfJsonObject(body))
                .queryParams(queryParams)
                .expect()
                .spec(resp)
                .when()
                .post(endpoint, params);
    }

    @Step("post {endpoint}")
    public Response postWithCookiesMultiPart(ResponseSpecification resp,
                                             String endpoint,
                                             JSONObject body,
                                             Map<String, String> queryParams,
                                             Map<String, String> cookies,
                                             Object... params) {

        RequestSpecification specification = getReqWithApiKey().contentType(MULTIPART);

        body.keys().forEachRemaining(key -> {
            Object value = body.get(key);
            specification.multiPart(key, value);
        });

        return specification
                .cookies(cookies)
                .queryParams(queryParams)
                .expect()
                .spec(resp)
                .when()
                .post(endpoint, params);
    }

    @Step("post {endpoint}")
    public Response postWithCookiesMultiPart(ResponseSpecification resp,
                                             String endpoint,
                                             JSONObject body,
                                             Map<String, String> queryParams,
                                             Map<String, String> cookies,
                                             Map<String, String> header,
                                             Object... params) {

        RequestSpecification specification = getReqWithApiKey().contentType(MULTIPART);

        body.keys().forEachRemaining(key -> {
            Object value = body.get(key);
            specification.multiPart(key, value);
        });

        return specification
                .headers(header)
                .cookies(cookies)
                .queryParams(queryParams)
                .expect()
                .spec(resp)
                .when()
                .post(endpoint, params);
    }

    @Step("post {endpoint}")
    public Response postWithHeader(ResponseSpecification resp, String endpoint, JSONObject body, Map<String, ?> headers, Object... params) {
        return getReqWithApiKey()
                .body(formatIfJsonObject(body))
                .headers(headers)
                .expect()
                .spec(resp)
                .when()
                .post(endpoint, params);
    }

    @Step("post {endpoint}")
    public Response postWithHeader(ResponseSpecification resp, String endpoint, Map<String, ?> headers, Object... params) {
        return getReqWithApiKey()
                .headers(headers)
                .expect()
                .spec(resp)
                .when()
                .post(endpoint, params);
    }

    @Step("post {endpoint}")
    public Response postWithImage(ResponseSpecification resp,
                                  String endpoint,
                                  String imagePath,
                                  Integer targetType,
                                  long time,
                                  String targetUID) {
        return getReqWithApiKey()
                .contentType("multipart/form-data")
                .multiPart("target_uid", targetUID)
                .multiPart("target_type", targetType)
                .multiPart("timestamp", time)
                .multiPart("body", new File(imagePath), "image/*")
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }

    @Step("post {endpoint}")
    public Response postFile(ResponseSpecification resp, String endpoint, String filePath, String mimeType) {
        return getReqWithApiKey()
                .contentType("multipart/form-data")
                .multiPart("file", new File(filePath), mimeType)
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }

    @Step("post {endpoint}")
    public Response postInvalidFile(ResponseSpecification resp, String endpoint, String filePath) {
        return getReqWithApiKey()
                .contentType("multipart/form-data")
                .multiPart("file", new File(filePath))
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }

    @Step("delete {endpoint}")
    public Response delete(ResponseSpecification resp, String endpoint, Object... pathParams) {
        return getReqWithApiKey()
                .expect()
                .spec(resp)
                .when()
                .delete(endpoint, pathParams);
    }

    @Step("delete {endpoint}")
    public Response deleteWithBody(ResponseSpecification resp, String endpoint, Map<String, Integer> body) {
        return getReqWithApiKey()
                .contentType("multipart/form-data")
                .multiPart((MultiPartSpecification) body)
                .expect()
                .spec(resp)
                .when()
                .delete(endpoint);
    }

    @Step("delete {endpoint}")
    public Response delete(ResponseSpecification resp, String endpoint, Map<String, ?> queryParams, Object... pathParams) {
        return getReqWithApiKey()
                .queryParams(queryParams)
                .expect()
                .spec(resp)
                .when()
                .delete(endpoint, pathParams);
    }

    @Step("delete {endpoint}")
    public Response deleteWithHeader(ResponseSpecification resp, String endpoint, Map<String, ?> queryParams, Map<String, ?> headers, Object... pathParams) {
        return getReqWithApiKey()
                .headers(headers)
                .params(queryParams)
                .expect()
                .spec(resp)
                .when()
                .delete(endpoint, pathParams);
    }

    @Step("delete {endpoint}")
    public Response deleteWithHeader(ResponseSpecification resp, String endpoint, Map<String, ?> headers, Object... pathParams) {
        return getReqWithApiKey()
                .headers(headers)
                .expect()
                .spec(resp)
                .when()
                .delete(endpoint, pathParams);
    }

    @Step("put {endpoint}")
    public Response put(Object body, ResponseSpecification resp, String endpoint) {
        return getReqWithApiKey()
                .body(body)
                .expect()
                .spec(resp)
                .when()
                .put(endpoint);
    }

    @Step("put {endpoint}")
    public Response put(ResponseSpecification resp, String endpoint, Object body) {
        return getReqWithApiKey()
                .body(formatIfJsonObject(body))
                .expect()
                .spec(resp)
                .when()
                .put(endpoint);
    }

    @Step("put {endpoint}")
    public Response put(ResponseSpecification resp, String endpoint, Object body, Object... pathParams) {
        return getReqWithApiKey()
                .body(formatIfJsonObject(body))
                .expect()
                .spec(resp)
                .when()
                .put(endpoint, pathParams);
    }

    @Step("patch {endpoint}")
    public Response patch(ResponseSpecification resp, String endpoint, Object body) {
        return getReqWithApiKey()
                .body(formatIfJsonObject(body))
                .expect()
                .spec(resp)
                .when()
                .patch(endpoint);
    }

    @Step("patch {endpoint}")
    public Response patch(ResponseSpecification resp, String endpoint, Object body, Object... pathParams) {
        return getReqWithApiKey()
                .body(formatIfJsonObject(body))
                .expect()
                .spec(resp)
                .when()
                .patch(endpoint, pathParams);
    }

    private Object formatIfJsonObject(Object body) {
        Object formattedBody = body;
        if (body instanceof JSONObject) {
            formattedBody = body.toString();
        }
        return formattedBody;
    }

    @Step("post {endpoint}")
    public Response postMultipart(ResponseSpecification resp,
                                  String endpoint,
                                  Map<String, Object> queryParams,
                                  MultiPartSpecification... multiParts) {
        RequestSpecification specification = getReqWithApiKey()
                .contentType(MULTIPART);
        for (MultiPartSpecification multiPart : multiParts) {
            specification.multiPart(multiPart);

        }
        return specification
                .queryParams(queryParams)
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }

    @Step("post {endpoint}")
    public Response postMultipart(ResponseSpecification resp, String endpoint, MultiPartSpecification... multiParts) {
        RequestSpecification specification = getReqWithApiKey()
                .contentType(MULTIPART);
        for (MultiPartSpecification multiPart : multiParts) {
            specification.multiPart(multiPart);

        }
        return specification
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }

    @Step("post {endpoint}")
    public Response postMultipart(ResponseSpecification resp, String endpoint, List<MultiPartSpecification> multiParts) {
        RequestSpecification specification = getReqWithApiKey()
                .contentType(MULTIPART);
        for (MultiPartSpecification multiPart : multiParts) {
            specification.multiPart(multiPart);
        }
        return specification
                .expect()
                .spec(resp)
                .when()
                .post(endpoint);
    }
}