package com.siemens.sl.apigateway.retrofit;

import com.siemens.sl.apigateway.model.GetUserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RemoteServices {

    @GET("/api-gateway-request/getUser/{username}")
    Call<GetUserResponse> getUser(@Path("username") String username);


}
