package com.github.apollodemo.typeAdapter;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import com.github.apollodemo.api.GQLClient;

import org.jetbrains.annotations.NotNull;

import java.net.URI;

/**
 * Adapter to customize mapping for {@link URI}. Without this adapter get the error:
 * java.lang.IllegalArgumentException: Can't map GraphQL type: URI to: class java.net.URI. Did you forget to add custom type adapter?
 * To fix error add this adapter to {@link ApolloClient}
 * @see GQLClient#getApolloClient()
 *
 * See build.gradle how to add mapping
 *
 * @author Ivan V on 26.08.2019.
 * @version 1.0
 */
public class CustomURIAdapter implements CustomTypeAdapter<URI> {

    @Override
    public URI decode(@NotNull CustomTypeValue value) {
        try {
            return URI.create(value.value.toString());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public CustomTypeValue encode(@NotNull URI value) {
        return new CustomTypeValue.GraphQLString(value.getPath());
    }

}
