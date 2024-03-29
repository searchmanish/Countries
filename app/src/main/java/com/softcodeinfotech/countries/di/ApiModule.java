package com.softcodeinfotech.countries.di;

import com.softcodeinfotech.countries.model.CountriesApi;
import com.softcodeinfotech.countries.model.CountriesService;
import com.softcodeinfotech.countries.viewmodel.ListViewModel;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    public static final String BASE_URL = "https://raw.githubusercontent.com/";

    @Provides
    public CountriesApi provideCountryApi()
    {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CountriesApi.class);
    }

    @Provides
    public CountriesService provideCountriesService()
    {
        return CountriesService.getInstance();
    }

}
