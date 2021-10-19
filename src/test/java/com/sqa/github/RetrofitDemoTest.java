package com.sqa.github;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sqa.model.github.Issue;
import com.sqa.services.GitHubService;
import com.sqa.services.GorestService;
import com.sqa.services.LoggingInterceptor;
import com.sqa.utils.TestLogger;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RetrofitDemoTest implements TestLogger {

    private Retrofit retrofitGit;
    private Retrofit retrofitGorest;
    private GitHubService gitHubService;
    private GorestService gorestService;
    private static final String GIT_HUB_URL = "https://api.github.com/";
    private static final String GOREST_URL = "https://gorest.co.in/";

    private String issueTitle = String.format("issue %s", RandomStringUtils.randomAlphabetic(5));
    private String issueDescription = "Description of new issue";

    public RetrofitDemoTest() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .readTimeout(Duration.of(5, ChronoUnit.SECONDS))
//                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        this.retrofitGit = new Retrofit.Builder()
                .baseUrl(GIT_HUB_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.gitHubService = retrofitGit.create(GitHubService.class);
    }

    /*
        01. Проверяем, что приходит 200 код в ответ на простой GET
    */
    @Test
    @DisplayName("Healthcheck test")
    public void verifyHealthcheckTest() throws IOException {
        assertEquals(
                gitHubService.getZen().execute().code(),
                200,
                "Zen response code");
    }

    /*
        02. Проверяем, что приходит непустое тело ответа на простой GET
    */
    @Test
    public void verifyDefunktBodyTest() throws IOException {
        assertFalse(gitHubService.getDefunkt().execute().body().isEmpty());
    }

    /*
        03. Проверяем, что тело ответа содержит поле, равное значению
    */
    @Test
    public void verifyIssuesContainNoAuthTest() throws IOException {
        Response<String> response = gitHubService.getIssuesNoAuth("ilyademchenko").execute();
//        assertEquals(response.code(), 200);
        log(response.raw().message());
        assertTrue(response.raw().message().contains("Not Found"));
    }

    /*
        04. Проверяем, что тело ответа содержит поле после авторизации
    */
    @Test
    public void verifyIssuesAuthorized() throws IOException {
        Response<List<Issue>> response =
                gitHubService.getUsersIssues(
                        "Bearer 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",
                        "ilyademchenko").execute();

        assertTrue(response.body().stream()
                .anyMatch(issue -> issue.getTitle().equals("lux-training Vladimir")));
    }

    /*
        05. Проверяем, что тело ответа содержит ошибку и 403 код
    */
    @Test
    public void verifyIssuesNoUserAgent() throws IOException {
        Response<List<Issue>> response =
                gitHubService.getUsersIssuesWithAcceptHeader(
                        "application/xml",
                        "Bearer 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",
                        "ilyademchenko")
                        .execute();
        assertEquals(response.code(), 415);
    }

    /*
        06. Проверяем, что ишью публикуется
    */
    @Test
    public void verifyPostIssues() throws IOException {
        String body = "{\n" +
                "    \"title\":\"lux-training 09\",\n" +
                "    \"body\": \"Description of issue\"\n" +
                "}";
        Response<String> response =
                gitHubService.postIssue(
                                "application/json",
                                "Bearer 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",
                                "ilyademchenko",
                                body)
                        .execute();
        assertAll(
                () -> assertEquals(201, response.code()),
                () -> assertTrue(response.body().contains("lux-training 09"))
        );
    }

    /*
        07. Проверяем, что тело ответа содержит ошибку и 403 код
    */
    @Test
    public void verifyPostIssuesUrlParam() throws IOException {

    }

    /*
        08. Проверяем, что ишью публикуется (тело запроса в POJO)
    */
    @Test
    public void verifyPostPojo() throws IOException {
        Issue requestIssue = new Issue();
        requestIssue
                .setTitle(issueTitle)
                .setBody(issueDescription);

        Response<Issue> response =
                gitHubService.postIssuePojo(
                                "application/json",
                                "Bearer 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",
                                "ilyademchenko",
                                requestIssue)
                        .execute();

        assertAll(
                () -> assertEquals(201, response.code()),
                () -> assertEquals(issueTitle, response.body().getTitle()),
                () -> assertEquals(issueDescription, response.body().getBody())
        );
    }

    /*
        09. Проверяем, что ишью публикуется (тело запроса в Map)
    */
    @Test
    public void verifyPostMap() throws IOException {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("title", issueTitle);
        requestBody.put("body", issueDescription);

        Response<Map<String, Object>> response =
                gitHubService.postIssueMap(
                                "application/json",
                                "Bearer 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",
                                "ilyademchenko",
                                requestBody)
                        .execute();

        assertAll(
                () -> assertEquals(201, response.code()),
                () -> assertEquals(issueTitle, response.body().get("title")),
                () -> assertEquals(issueDescription, response.body().get("body"))
        );
    }

}
