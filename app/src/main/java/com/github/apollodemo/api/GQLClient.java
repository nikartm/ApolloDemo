package com.github.apollodemo.api;

import androidx.annotation.NonNull;

import com.apollographql.apollo.ApolloClient;
import com.github.apollodemo.BuildConfig;
import com.github.apollodemo.type.CustomType;
import com.github.apollodemo.typeAdapter.CustomURIAdapter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * GraphQL Apollo Client
 *
 * @author Ivan V on 25.08.2019.
 * @version 1.0
 */
public class GQLClient {

    private static final String BASE_URL = "https://api.github.com/graphql";
    private static final String TOKEN = "bearer " + BuildConfig.TOKEN;

    public static ApolloClient getApolloClient() {
        OkHttpClient okHttpClient = getClient();
        return ApolloClient.builder()
                .okHttpClient(okHttpClient)
                .serverUrl(BASE_URL)
                .addCustomTypeAdapter(CustomType.URI, new CustomURIAdapter())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        httpClient
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(35, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Authorization", TOKEN);
                    return chain.proceed(requestBuilder.build());
                });
        return httpClient.build();
    }

}
