package com.siemens.sl.apigateway.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitConfig {

	@Bean
	public static RemoteServices getRetrofit() {
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC));
		Retrofit retrofit = new Retrofit.Builder()
		  //.baseUrl("http://172.18.170.30:7004")
				.baseUrl("http://localhost:8085")
		  .addConverterFactory(GsonConverterFactory.create())
		  .client(httpClient.build())
		  .build();

		return retrofit.create(RemoteServices.class);
	}
	
}
