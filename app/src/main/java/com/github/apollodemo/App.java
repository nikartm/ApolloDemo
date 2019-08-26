package com.github.apollodemo;

import android.app.Application;
import android.content.Context;

import com.apollographql.apollo.ApolloClient;
import com.github.apollodemo.api.GQLClient;

/**
 * @author Ivan V on 25.08.2019.
 * @version 1.0
 */
public class App extends Application {

    private static App context;
    private static ApolloClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        client = GQLClient.getApolloClient();
    }

    public static Context getAppContext() {
        return context;
    }

    public static ApolloClient getApolloClient() {
        return client;
    }

}
