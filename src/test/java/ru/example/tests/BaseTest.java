package ru.example.tests;

import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.example.config.AppConfigProvider;
import ru.example.helpers.api.petStore.PetStoreApiHelper;
import ru.example.listeners.SeverityToDisplayName;
import ru.example.listeners.TmsLinkToDisplayName;

import static io.restassured.RestAssured.expect;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@ExtendWith(SeverityToDisplayName.class)
@ExtendWith(TmsLinkToDisplayName.class)
public class BaseTest {
    public static ResponseSpecification resp200 = expect().statusCode(200);
    public static ResponseSpecification resp404 = expect().statusCode(404);
    public static ResponseSpecification resp400 = expect().statusCode(400);
    public static ResponseSpecification resp405 = expect().statusCode(405);
    public static ResponseSpecification resp403 = expect().statusCode(403);
    public static ResponseSpecification resp301 = expect().statusCode(301);
    public static ResponseSpecification resp401 = expect().statusCode(401);
    public static ResponseSpecification resp500 = expect().statusCode(500);
    public static ResponseSpecification resp502 = expect().statusCode(502);

    public static PetStoreApiHelper petStoreApi = new PetStoreApiHelper(AppConfigProvider.props().uri());
}
