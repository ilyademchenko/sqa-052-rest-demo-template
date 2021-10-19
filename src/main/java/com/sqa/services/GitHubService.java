package com.sqa.services;

import com.sqa.model.github.Issue;
import io.qameta.allure.Step;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface GitHubService {

    String ISSUES_ENDPOINT_PATH = "repos/{user}/rest/issues";

    @Step
    @GET("zen")
    Call<String> getZen();

    @Step
    @GET("users/defunkt")
    Call<String> getDefunkt();

    @Step
    @GET(ISSUES_ENDPOINT_PATH)
    Call<String> getIssuesNoAuth(@Path("user") String user);

    @Step
    @GET(ISSUES_ENDPOINT_PATH)
    Call<List<Issue>> getUsersIssues(
            @Header("Authorization") String authToken,
            @Path("user") String user
    );

    @Step
    @GET(ISSUES_ENDPOINT_PATH)
    Call<List<Issue>> getUsersIssuesWithAcceptHeader(
            @Header("Accept") String acceptHeader,
            @Header("Authorization") String authToken,
            @Path("user") String user
    );

    @Step
    @POST("repos/{user}/rest/issues")
    Call<Issue> postIssuePojo(
            @Header("Accept") String acceptHeader,
            @Header("Authorization") String authToken,
            @Path("user") String user,
            @Body Issue body
    );

    @Step
    @POST("repos/{user}/rest/issues")
    Call<String> postIssue(
            @Header("Accept") String acceptHeader,
            @Header("Authorization") String authToken,
            @Path("user") String user,
            @Body String body);

    @Step
    @POST("repos/{user}/rest/issues")
    Call<Map<String, Object>> postIssueMap(
            @Header("Accept") String acceptHeader,
            @Header("Authorization") String authToken,
            @Path("user") String user,
            @Body Map<String, Object> body);
}
