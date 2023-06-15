package com.capstone_bangkit.fitnessist.api

import com.capstone_bangkit.fitnessist.model.FoodPredictionList

class FoodResponse (
    val status: StatusResponse,
    val data: FoodPredictionList
)