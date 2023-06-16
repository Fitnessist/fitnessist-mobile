package com.capstone_bangkit.fitnessist.api

import com.capstone_bangkit.fitnessist.model.ExerciseProgress
import com.capstone_bangkit.fitnessist.model.workouts.MyProgram
import com.capstone_bangkit.fitnessist.model.workouts.Program
import com.capstone_bangkit.fitnessist.model.workouts.Workout
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
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
        @Body request: PostTDEECalculationRequest
    ): Call<AddTDEECalculationResponse>

    @PUT("users/properties")
    fun putTDEECalculation(
        @Header("Authorization") token: String,
        @Body request: PutTDEECalculationRequest
    ): Call<PutTDEECalculationResponse>

    @GET("programs/{id}")
    fun getProgramByID(
        @Path("id") programId: String
    ): Call<ResponseJSON<Program>>

    @GET("programs")
    fun getAllPrograms(
    ): Call<ResponseJSON<PaginationData<Program>>>

    @GET("workouts")
    fun getAllWorkoutsWithProgramId(
        @Query("program_id") programId: String?
    ): Call<ResponseJSON<PaginationData<Workout>>>

    @GET("workouts/{id}")
    fun getWorkoutById(
        @Path("id") workoutId: String
    ): Call<ResponseJSON<Workout>>

    @POST("my-program")
    fun postMyProgram(
        @Header("Authorization") token: String,
        @Body() request: AddMyProgramRequest
    ): Call<ResponseJSON<MyProgram>>

    @GET("my-program")
    fun getMyProgramWithProgramId(
        @Header("Authorization") token: String,
        @Query("program_id") programId: String?
    ): Call<ResponseJSON<MyProgram>>

    @GET("my-program")
    fun getMyProgram(
        @Header("Authorization") token: String,
    ): Call<ResponseJSON<List<MyProgram>>>

    @POST("my-progress")
    fun postExerciseProgress(
        @Header("Authorization") token: String,
        @Body request: ExerciseProgress
    ): Call<ResponseJSON<ExerciseProgress>>

    @GET("my-progress")
    fun getExerciseProgress(
        @Header("Authorization") token: String,
    ): Call<ResponseJSON<List<ExerciseProgress>>>

}