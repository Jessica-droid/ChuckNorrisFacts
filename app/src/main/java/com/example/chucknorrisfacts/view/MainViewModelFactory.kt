package com.example.chucknorrisfacts.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chucknorrisfacts.services.Injector
import java.lang.IllegalArgumentException

class MainViewModelFactory() : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(Injector.searchService) as T
        }

        throw IllegalArgumentException("Invalid viewModel")

    }


}