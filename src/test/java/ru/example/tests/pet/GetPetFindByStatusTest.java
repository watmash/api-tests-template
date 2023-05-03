package ru.example.tests.pet;

import io.qameta.allure.Severity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.example.helpers.api.petStore.EndpointsPetStore;
import ru.example.tests.BaseTest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.params.provider.Arguments.of;
import static ru.example.helpers.allure.AllureAssertions.assertThat;
import static ru.example.helpers.allure.AllureHelper.setTmsLink;

@DisplayName("GET /v2/pet/findByStatus")
public class GetPetFindByStatusTest extends BaseTest {

    @BeforeAll
    void beforeAllTestsWithoutStatic() {
        System.out.println("Prepare smth to test");
    }

    @Severity(CRITICAL)
    @MethodSource("statuses")
    @ParameterizedTest(name = "Выполнить GET /v2/pet/findByStatus с status = {0}")
    void correctRequest(String status, int tmsLink) {
        setTmsLink(tmsLink);

        Map<String, Object> params = Map.of("status", status);
        List<String> statusesList = petStoreApi.get(resp200, EndpointsPetStore.PET_FIND_BY_STATUS, params)
                .jsonPath()
                .getList("status");
        Set<String> originalStatuses = new HashSet<>(statusesList);
        assertThat(originalStatuses, Matchers.is(Set.of(status)), String.format("В ответе только статус %s", status));
    }

    private Stream<Arguments> statuses() {
        return Stream.of(
                of(
                        "available",
                        12345
                ), of(
                        "pending",
                        12346
                ), of(
                        "sold",
                        12347
                )
        );
    }
}
