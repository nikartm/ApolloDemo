package com.github.apollodemo;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.github.apollodemo.api.GQLClient;

/**
 * @author Ivan V on 25.08.2019.
 * @version 1.0
 */
public class App extends Application {

    private static ApolloClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        client = GQLClient.getApolloClient();
    }

    public static ApolloClient getApolloClient() {
        return client;
    }

}
