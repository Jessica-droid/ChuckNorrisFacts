package com.example.chucknorrisfacts.view

import com.example.chucknorrisfacts.domain.model.ChuckFact

sealed class SearchStates {

    object Loading : SearchStates()
    data class Success(val facts : List<ChuckFact>) : SearchStates()
    data class Error(val reason : Throwable) : SearchStates()


}