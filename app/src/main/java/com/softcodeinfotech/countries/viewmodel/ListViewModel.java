package com.softcodeinfotech.countries.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.softcodeinfotech.countries.di.DaggerApiComponent;
import com.softcodeinfotech.countries.model.CountriesService;
import com.softcodeinfotech.countries.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {
    public MutableLiveData<List<CountryModel>> countries = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countryLoadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    //CountriesService countriesService = CountriesService.getInstance();
    @Inject
    public CountriesService countriesService;
    @Inject
    public CompositeDisposable disposable;

    public ListViewModel()
    {
        super();
        DaggerApiComponent.create().injectCountryService(this);
    }

   // private CompositeDisposable disposable = new CompositeDisposable();

    public void refresh()
    {
    fetchCountries();
    }

    private void fetchCountries() {

        loading.setValue(true);
        disposable.add(
                countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>()
                {

                    @Override
                    public void onSuccess(List<CountryModel> countryModels) {
                        countries.setValue(countryModels);
                        countryLoadError.setValue(false);
                        loading.setValue(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        countryLoadError.setValue(true);
                        loading.setValue(false);
                        e.printStackTrace();
                    }
                })
        );


    }
   /* private void fetchCountries()
    {
        CountryModel country1 = new CountryModel("India","Delhi","");
        CountryModel country2 = new CountryModel("America","Washington","");
        CountryModel country3 = new CountryModel("Pakistan","Islamabad","");

        List<CountryModel> list = new ArrayList<>();
        list.add(country1);
        list.add(country2);
        list.add(country3);

        list.add(country1);
        list.add(country2);
        list.add(country3);

        list.add(country1);
        list.add(country2);
        list.add(country3);
        countries.setValue(list);
        countryLoadError.setValue(false);
        loading.setValue(false);

    }*/


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
