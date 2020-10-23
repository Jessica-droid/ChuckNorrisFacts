package com.example.chucknorrisfacts.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chucknorrisfacts.domain.SearchService
import com.example.chucknorrisfacts.domain.model.ChuckFact
import io.reactivex.Observable

class MainViewModel(private val service: SearchService) : ViewModel() {

    fun search(query: String): Observable<SearchStates> =
        service.searchWith(query).map { SearchStates.Success(it) as SearchStates }
            .startWith(SearchStates.Loading).onErrorReturn { SearchStates.Error(it)}

}