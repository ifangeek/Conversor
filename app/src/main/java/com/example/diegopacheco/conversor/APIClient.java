package com.example.diegopacheco.conversor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.diegopacheco.conversor.BuildConfig.ACCESS_KEY;

/**
 * Created by DIEGO on 1/03/2018.
 */

public class APIClient implements Interceptor{

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://apilayer.net";


    @Override
    public Response intercept(Chain chain) throws IOException{

        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter("access_key",ACCESS_KEY).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
    Retrofit getClient(){
        HttpLoggingInterceptor interceptor  = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new
                OkHttpClient.Builder().addInterceptor(interceptor)
                                        .addInterceptor(this).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;

    }

}
