package com.capstone_bangkit.fitnessist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.capstone_bangkit.fitnessist.api.AddMyProgramRequest
import com.capstone_bangkit.fitnessist.api.ApiConfig
import com.capstone_bangkit.fitnessist.api.PaginationData
import com.capstone_bangkit.fitnessist.api.ResponseJSON
import com.capstone_bangkit.fitnessist.model.workouts.MyProgram
import com.capstone_bangkit.fitnessist.model.workouts.Program
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProgramViewModel(application: Application) : AndroidViewModel(application) {
    val myProgramsData: MutableLiveData<List<MyProgram>> = MutableLiveData()
    val programs: MutableLiveData<List<Program>> = MutableLiveData()
    val TAG = "HomeViewModel"

    fun addUserProgram(token: String, programId: String, onSuccess: (MyProgram) -> Unit, onError: (String) -> Unit) {
        // Mengirim permintaan ke backend
        ApiConfig.getApiService().postMyProgram("Bearer $token", AddMyProgramRequest(programId)).enqueue(object :
            Callback<ResponseJSON<MyProgram>> {
            override fun onResponse(call: Call<ResponseJSON<MyProgram>>, response: Response<ResponseJSON<MyProgram>>) =
                if (response.isSuccessful) {
                    val addedMyProgram = response.body()!!.data
                    if (addedMyProgram != null) {
                        onSuccess(addedMyProgram)
                    } else {
                        onError("Server Error Coba lagi Setelah Beberapa Saat")
                    }
                } else {
                    onError("Server Error Coba lagi Setelah Beberapa Saat")
                }

            override fun onFailure(call: Call<ResponseJSON<MyProgram>>, t: Throwable) {
                onError(t.message ?: "Server Error Coba lagi Setelah Beberapa Saat")
            }
        })
    }


    fun getAllProgram(onSuccess: (List<Program>) -> Unit, onError: (String) -> Unit) {
        ApiConfig.getApiService().getAllPrograms().enqueue(object : Callback<ResponseJSON<PaginationData<Program>>> {
            override fun onResponse(call: Call<ResponseJSON<PaginationData<Program>>>, response: Response<ResponseJSON<PaginationData<Program>>>) =
                if (response.isSuccessful) {
                    val addedMyProgram = response.body()!!.data!!.items
                    if (addedMyProgram != null) {
                        onSuccess(addedMyProgram)
                    } else {
                        onError("Server Error Coba lagi Setelah Beberapa Saat")
                    }
                } else {
                    onError("Server Error Coba lagi Setelah Beberapa Saat")
                }

            override fun onFailure(call: Call<ResponseJSON<PaginationData<Program>>>, t: Throwable) {
                onError(t.message ?: "Server Error Coba lagi Setelah Beberapa Saat")
            }
        })
    }

}