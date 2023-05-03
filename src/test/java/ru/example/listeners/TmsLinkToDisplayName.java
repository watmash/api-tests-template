package ru.example.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Link;
import io.qameta.allure.util.ResultsUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.ArrayList;
import java.util.List;

public class TmsLinkToDisplayName implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) {
        Allure.getLifecycle().updateTestCase(testResult -> {

            List<Link> links = testResult.getLinks();
            List<String> tmsLinkIds = new ArrayList<>();
            String name = testResult.getName();
            for (Link link : links) {
                if (ResultsUtils.TMS_LINK_TYPE.equals(link.getType())
                        && !name.contains(link.getName())) {
                    tmsLinkIds.add(link.getName());
                }
            }

            if (!tmsLinkIds.isEmpty()) {
                String originalName = testResult.getName();
                String nameWithTmsIds = String.format("%s %s", tmsLinkIds, originalName);
                testResult.setName(nameWithTmsIds);
            }
        });
    }
}
