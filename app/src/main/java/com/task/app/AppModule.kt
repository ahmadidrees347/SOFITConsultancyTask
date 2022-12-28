package com.task.app

import com.task.api.DrinksApi
import com.task.utils.NotificationGenerator
import com.task.repository.DrinksRepo
import com.task.repository.FavDrinksRepo
import com.task.utils.SharedPrefUtils
import com.task.viewmodel.DrinksViewModel
import com.task.viewmodel.FavDrinksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val getModule = module {

        single { DrinksApi() }
        single { DrinksRepo(get()) }
        single { FavDrinksRepo(get()) }
        single { SharedPrefUtils(get()) }
        single { NotificationGenerator(get()) }

        viewModel { DrinksViewModel(get()) }
        viewModel { FavDrinksViewModel(get()) }
    }
}