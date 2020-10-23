package com.example.chucknorrisfacts.services

import com.example.chucknorrisfacts.domain.SearchService
import retrofit2.Retrofit

object Injector {

    val searchService : SearchService = buildService()

   private fun buildService() : SearchService{

        val retrofit = RetrofitBuilder.build()
        val serviceApi = retrofit.create<SearchApi>(SearchApi::class.java)

        return SearchInfrastructure(serviceApi)

    }
}