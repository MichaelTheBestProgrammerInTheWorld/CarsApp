package com.michaelmagdy.carsapp.webservice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Call<Response> getCars (String url);
}
