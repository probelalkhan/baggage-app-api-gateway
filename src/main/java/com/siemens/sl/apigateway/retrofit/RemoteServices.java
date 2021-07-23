package com.siemens.sl.apigateway.retrofit;

import com.siemens.sl.apigateway.model.AddUserRequest;
import com.siemens.sl.apigateway.model.GetUserResponse;
import com.siemens.sl.apigateway.model.UserTable;
import com.siemens.sl.apigateway.model.v1.Group;
import com.siemens.sl.apigateway.model.v1.User;
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

    @GET("/api-gateway-request/users")
    Call<List<User>> getAllUsers();

    @POST("/api-gateway-request/addgroup")
    Call<Group> addGroup(@Body Group group);

    @GET("/api-gateway-request/groups")
    Call<List<Group>> getAllGroups();

}
