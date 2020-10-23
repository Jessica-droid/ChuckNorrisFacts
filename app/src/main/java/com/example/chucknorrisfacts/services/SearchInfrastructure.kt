package com.example.chucknorrisfacts.services

import com.example.chucknorrisfacts.domain.SearchService
import com.example.chucknorrisfacts.domain.model.ChuckFact
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers.io

internal class SearchInfrastructure(private val searchApi: SearchApi) : SearchService {

    override fun searchWith(query: String): Observable<List<ChuckFact>> {
        return searchApi.searchWith(query).map { response -> FactMapper(response) }.subscribeOn(
            io()
        ).compose(RetrofitErrorHandler())
    }

}