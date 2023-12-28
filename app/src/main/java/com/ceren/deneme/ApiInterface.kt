package com.ceren.deneme


import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Headers

interface ApiInterface {


    @Headers("X-RapidAPI-Key: 9db838c6dbmsh6ab1d137a64dee9p1c8ddcjsnd05f7c9484e3",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com")


    @GET("search")
    fun getData(@Query("q") query:String) : Call<MyData>
}