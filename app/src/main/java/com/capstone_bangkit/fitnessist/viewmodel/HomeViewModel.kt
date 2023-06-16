package com.capstone_bangkit.fitnessist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone_bangkit.fitnessist.api.ApiConfig
import com.capstone_bangkit.fitnessist.api.ResponseJSON
import com.capstone_bangkit.fitnessist.model.workouts.MyProgram
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class HomeViewModel: ViewModel() {
    val myProgramData: MutableLiveData<MyProgram> = MutableLiveData()
    val TAG = "HomeViewModel"

    fun getMyProgramData(token: String, programId: String?) {
        val myProgramCall: Call<ResponseJSON<MyProgram>>
        val accessToken = "Bearer $token"

        myProgramCall = ApiConfig.getApiService().getMyProgramWithProgramId(accessToken, programId)
        myProgramCall.enqueue(object : Callback<ResponseJSON<MyProgram>> {
            override fun onResponse(call: Call<ResponseJSON<MyProgram>>, response: Response<ResponseJSON<MyProgram>>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data!!
                    myProgramData.postValue(data)
                }
            }

            override fun onFailure(call: Call<ResponseJSON<MyProgram>>, t: Throwable) {
                // Menangani kegagalan permintaan API
                Log.d(TAG,"something went wrong")
            }
        })
    }
}

