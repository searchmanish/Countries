package com.softcodeinfotech.countries.model;

import com.softcodeinfotech.countries.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CountriesService {


    private static CountriesService instance;

    @Inject
    public CountriesApi api;

  /*  private CountriesApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi.class);*/

    private CountriesService() {
        DaggerApiComponent.create().inject(this);

    }

    public static CountriesService getInstance() {
        if (instance == null) {
            instance = new CountriesService();
        }
        return instance;
    }

    public Single<List<CountryModel>> getCountries() {
        return api.getCountries();
    }


}
