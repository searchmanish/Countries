package com.softcodeinfotech.countries.di;

import com.softcodeinfotech.countries.model.CountriesService;
import com.softcodeinfotech.countries.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(CountriesService service);
    void injectCountryService(ListViewModel listViewModel);

}
