package com.example.chucknorrisfacts.services

import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.HttpException

class RetrofitErrorHandler <T> : ObservableTransformer <T,T>{

    private val gson = Gson()

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.onErrorResumeNext { error: Throwable ->
            when (error) {
                is HttpException -> {
                    Observable.error(
                        gson.fromJson<ErrorBody>(
                            error.response().errorBody()?.string(), ErrorBody::class.java
                        )
                    )
                }
                else -> Observable.error(error)
            }
        }
    }


}