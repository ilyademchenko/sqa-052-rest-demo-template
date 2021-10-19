package com.sqa.github;

import com.sqa.model.github.Issue;
import com.sqa.utils.TestLogger;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestAssuredConfigurationDemoTests implements TestLogger {

    private static final String BASE_URL = "https://api.github.com/";
    private String issueTitle = String.format("issue %s", RandomStringUtils.randomAlphabetic(5));
    private String issueDescription = "Description of new issue";

    private static RequestSpecification request;

    @BeforeAll
    public static void init() {
        request = RestAssured.given()
                .baseUri(BASE_URL)
                .header("Accept", "application/json")
                .header("Authorization", "Bearer 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");
    }

    /*
        04. Проверяем, что тело ответа содержит поле после авторизации
    */
    @Test
    public void verifyIssuesAuthorized() {

    }

    /*
        06. Проверяем, что ишью публикуется (тело запроса в строке)
    */
    @Test
    public void verifyPostIssues() {

    }

    /*
        08. Проверяем, что ишью публикуется (тело запроса в POJO)
    */
    @Test
    public void verifyPostPojo() {

    }

    /*
        09. Проверяем, что ишью публикуется (тело запроса в Map)
    */
    @Test
    public void verifyPostMap() {

    }

    /*
        10. Проверяем, что ишью публикуется (тело запроса в POJO, поиск с помощью json path)
    */
    @Test
    public void verifyPostPojoWithJsonPath() {

    }
}
