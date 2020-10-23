package com.example.chucknorrisfacts.services

import com.google.gson.annotations.SerializedName

data class ErrorBody (@SerializedName("message") val errorMessage : String) : Throwable()