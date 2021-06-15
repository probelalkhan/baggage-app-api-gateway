package com.siemens.sl.apigateway.retrofit;

import com.siemens.sl.apigateway.model.AddUserRequest;
import com.siemens.sl.apigateway.model.GetUserResponse;
import com.siemens.sl.apigateway.model.UserTable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface RemoteServices {

    @GET("/api-gateway-request/getUser/{username}")
    Call<GetUserResponse> getUser(@Path("username") String username);

    @POST("/api-gateway-request/adduser")
    Call<AddUserRequest> addUser(@Body AddUserRequest request);

    @POST("/api-gateway-request/users")
    Call<List<UserTable>> getAllUsers();

}
