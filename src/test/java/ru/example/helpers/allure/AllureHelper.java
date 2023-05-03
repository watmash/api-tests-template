package ru.example.helpers.allure;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Link;
import io.qameta.allure.util.ResultsUtils;

import java.util.List;

public class AllureHelper {
    public static void setDisplayName(String testNameInAllure) {
        Allure.getLifecycle()
                .updateTestCase(testResult -> testResult.setName(testNameInAllure));
    }

    public static void setTmsLink(int tmsLinkName) {
        setTmsLink(String.valueOf(tmsLinkName));
    }

    public static void setTmsLink(String tmsLinkName) {
        Link tmsLink = ResultsUtils.createTmsLink(tmsLinkName);
        Allure.getLifecycle().updateTestCase(testResult -> {
            List<Link> originalLinks = testResult.getLinks();
            originalLinks.add(tmsLink);
            testResult.setLinks(originalLinks);
        });
    }

    public static void setTmsLink(List<Integer> tmsLinks) {
        Allure.getLifecycle().updateTestCase(testResult -> {
            List<Link> originalLinks = testResult.getLinks();
            for (Integer link : tmsLinks) {
                Link tmsLink = ResultsUtils.createTmsLink(String.valueOf(link));
                originalLinks.add(tmsLink);
            }
            testResult.setLinks(originalLinks);
        });
    }
}
