package com.capstone_bangkit.fitnessist.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("auth/register")
    fun authenticationRegister(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @POST("auth/login")
    fun authenticationLogin(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("foods/predict")
    fun foodPrediction(
        @Header("Authorization") token: String,
        @Body request: FoodRequest
    ): Call<FoodResponse>

    @POST("foods/my-histories")
    fun addFoodHistory(
        @Header("Authorization") token: String,
        @Body request: AddFoodHistoryRequest
    ): Call<AddFoodHistoryResponse>

    @GET("foods/my-histories")
    fun getFoodHistory(
        @Header("Authorization") token: String,
        @Query("date") date: String
    ): Call<GetFoodHistoryResponse>

    @GET("users/properties")
    fun getTDEECalculation(
        @Header("Authorization") token: String,
    ): Call<GetTDEECalculationResponse>
    @POST("users/properties")
    fun addTDEECalculation(
        @Header("Authorization") token: String,
        @Body request: TDEECalculationRequest
    ): Call<AddTDEECalculationResponse>

}