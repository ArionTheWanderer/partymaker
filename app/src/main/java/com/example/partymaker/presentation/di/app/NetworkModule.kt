package com.example.partymaker.presentation.di.app

import com.example.partymaker.Constants
import com.example.partymaker.data.network.api.CocktailApi
import com.example.partymaker.data.network.api.MealApi
import com.example.partymaker.presentation.di.activity.qualifiers.RetrofitCocktail
import com.example.partymaker.presentation.di.activity.qualifiers.RetrofitMeal
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @JvmStatic
    @Singleton
    @Provides
    @RetrofitMeal
    fun provideRetrofitMeal(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL_MEAL)
            .addConverterFactory(GsonConverterFactory.create())

    @JvmStatic
    @Singleton
    @Provides
    @RetrofitCocktail
    fun provideRetrofitCocktail(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL_COCKTAIL)
            .addConverterFactory(GsonConverterFactory.create())

    @JvmStatic
    @Singleton
    @Provides
    fun provideMealApi(@RetrofitMeal retrofit: Retrofit.Builder): MealApi {
        return retrofit.build().create(MealApi::class.java)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideCocktailApi(@RetrofitCocktail retrofit: Retrofit.Builder): CocktailApi {
        return retrofit.build().create(CocktailApi::class.java)
    }
}
