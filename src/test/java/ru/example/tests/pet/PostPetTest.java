package ru.example.tests.pet;

import io.qameta.allure.TmsLink;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.example.helpers.api.petStore.EndpointsPetStore;
import ru.example.tests.BaseTest;

import java.util.List;

@DisplayName("POST /v2/pet")
public class PostPetTest extends BaseTest {
    @Test
    @TmsLink("123458")
    @DisplayName("Add a new pet to the store")
    void createPet() {
        JSONObject body = new JSONObject()
                .put("name", "doggie")
                .put("photoUrls", List.of("photo_url"));
        long petId = petStoreApi.post(resp200, EndpointsPetStore.PET, body)
                .jsonPath()
                .getLong("id");

        petStoreApi.get(resp200, EndpointsPetStore.PET_BY_ID, petId);
    }
}
