package com.sqa.services;

import retrofit2.Call;
import retrofit2.http.*;

public interface GorestService {

    @POST("/public/v1/users/{user}/posts")
    Call<String> postIssueUrl(
            @Header("Authorization") String authToken,
            @Path("user") String user,
            @Query("title") String title,
            @Query("body") String body);

}
