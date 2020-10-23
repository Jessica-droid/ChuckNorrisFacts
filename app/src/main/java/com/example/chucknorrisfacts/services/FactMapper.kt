package com.example.chucknorrisfacts.services

import com.example.chucknorrisfacts.domain.model.ChuckFact
import com.example.chucknorrisfacts.domain.model.SearchResult

object FactMapper {

    operator fun invoke(response : SearchResult) : List<ChuckFact> = response.result.map { fact ->
        ChuckFact(categories = fact.categories, value = fact.value, url = fact.url)
    }

}