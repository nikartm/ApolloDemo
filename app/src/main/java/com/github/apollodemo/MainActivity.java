package com.github.apollodemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.rx2.Rx2Apollo;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Example of using GraphQL Apollo
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUser();
        getRepo();
    }

    // Example of using GraphQL Apollo with RxJava2
    private void getUser() {
        disposable = Rx2Apollo.from(App.getApolloClient().query(GetUserQuery.builder().build()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    Log.d(TAG, "Get user data: " + res.data().user());
                }, throwable -> {
                    Log.e(TAG, "onFailure: ", throwable);
                });
    }

    // See log
    private void getRepo() {
        App.getApolloClient().query(GetRepositoryQuery.builder()
                .repoName("Image-Support")
                .owner("nikartm")
                .build()
        ).enqueue(new ApolloCall.Callback<GetRepositoryQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetRepositoryQuery.Data> response) {
                Log.d(TAG, "Get repository data: " + response.data().repository);
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

}
