package com.avit.apnamzpadmin.utils;

import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;

public class ErrorUtils {

    public static NetworkResponse parseErrorResponse(Response<?> response){
        Gson gson = new Gson();
        try {
            String errorResponseString = response.errorBody().string();
            return gson.fromJson(errorResponseString,NetworkResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new NetworkResponse(false,"Unable to parse response");
        }
    }

}