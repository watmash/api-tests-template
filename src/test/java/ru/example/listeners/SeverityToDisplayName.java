package ru.example.listeners;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Label;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

public class SeverityToDisplayName implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Allure.getLifecycle().updateTestCase(testResult -> {
            List<Label> labels = testResult.getLabels();
            String severity = null;
            for (Label label : labels) {
                if ("severity".equalsIgnoreCase(label.getName())) {
                    severity = label.getValue();
                }
            }

            if (severity != null) {
                String originalName = testResult.getName();
                String nameWithSeverity = String.format("[%s] %s", severity.toUpperCase(), originalName);
                testResult.setName(nameWithSeverity);
            }
        });
    }
}
