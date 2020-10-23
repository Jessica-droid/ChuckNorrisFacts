package com.example.chucknorrisfacts.domain.model

class SearchResult(val result : ArrayList<FactResponse>)

class FactResponse(val value : String, val categories : Array<String>, val url : String)