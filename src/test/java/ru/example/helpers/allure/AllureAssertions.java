package ru.example.helpers.allure;

import io.qameta.allure.Step;
import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

public class AllureAssertions {
    @Step("Проверка: {msg}")
    public static void assertEquals(Object expected, Object actual, String msg) {
        Assertions.assertEquals(expected, actual, msg);
    }

    @Step("Проверка: {msg}")
    public static void assertAll(String msg, Executable... executables) {
        Assertions.assertAll(msg, executables);
    }

    @Step("Проверка: {msg}")
    public static void assertNotEquals(Object expected, Object actual, String msg) {
        Assertions.assertNotEquals(expected, actual, msg);
    }

    @Step("Проверка: {msg}")
    public static void assertTrue(boolean actual, String msg) {
        Assertions.assertTrue(actual, msg);
    }

    @Step("Проверка: {msg}")
    public static <T> void assertThat(T actual, Matcher<? super T> matcher, String msg) {
        MatcherAssert.assertThat(actual, matcher);
    }

    @Step("Проверка: {msg}")
    public static void assertFalse(boolean actual, String msg) {
        Assertions.assertFalse(actual, msg);
    }

    @Step("Проверка: {msg}")
    public static void assertArrays(List<Object> expected, List<Object> actual, String msg) {
        Assertions.assertArrayEquals(new List[]{expected}, new List[]{actual}, msg);
    }

    @Step("Проверка: {msg}")
    public static void assertSimilar(JSONObject expected, JSONObject actual, String msg) {
        Assertions.assertTrue(expected.similar(actual), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertSimilar(JSONArray expected, JSONArray actual, String msg) {
        boolean isSimilar = CollectionUtils.isEqualCollection(expected.toList(), actual.toList());
        Assertions.assertTrue(isSimilar, msg);
    }

    @Step("Проверка: {msg}")
    public static void assertStartsWith(String str, String expectedPrefix, String msg) {
        Assertions.assertTrue(str.startsWith(expectedPrefix), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertItemsStartsWith(List<String> items, String expectedPrefix, String msg) {
        Assertions.assertTrue(!items.isEmpty() && items.stream().allMatch(item -> item.startsWith(expectedPrefix)), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertItemsContains(List<String> items, String substring, String msg) {
        Assertions.assertTrue(!items.isEmpty() && items.stream().allMatch(item -> item.contains(substring)), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertMatch(String str, String pattern, String msg) {
        Assertions.assertTrue(str.matches(pattern), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertContains(String str, String expectedSubstr, String msg) {
        Assertions.assertTrue(str.contains(expectedSubstr), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertContains(List<String> items, String searchedItem, String msg) {
        Assertions.assertTrue(items.contains(searchedItem), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertContains(List<Object> items, Object searchedItem, String msg) {
        Assertions.assertTrue(items.contains(searchedItem), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertNotContains(List<Object> items, Object searchedItem, String msg) {
        Assertions.assertFalse(items.contains(searchedItem), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertNotNull(Object actual, String msg) {
        Assertions.assertNotNull(actual, msg);
    }

    @Step("Проверка: {msg}")
    public static void assertNull(Object actual, String msg) {
        Assertions.assertNull(actual, msg);
    }

    @Step("Проверка: {msg}")
    public static void assertContainsOnly(List<Object> expected, Object actual, String msg) {
        Assertions.assertTrue(!expected.isEmpty()
                && expected.stream().allMatch(actual::equals), msg);
    }

    @Step("Проверка: {msg}")
    public static void assertContainsOnlyIgnoreCase(List<String> expected, String actual, String msg) {
        Assertions.assertTrue(!expected.isEmpty()
                && expected.stream().allMatch(actual::equalsIgnoreCase), msg);
    }
}
