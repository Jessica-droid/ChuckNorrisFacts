package com.example.chucknorrisfacts.domain

import com.example.chucknorrisfacts.domain.model.ChuckFact
import io.reactivex.Observable


interface SearchService {
    fun searchWith(query: String): Observable<List<ChuckFact>>
}