package com.softcodeinfotech.countries.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softcodeinfotech.countries.R;
import com.softcodeinfotech.countries.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.countriesList)
    RecyclerView countriesList;

    @BindView(R.id.list_error)
    TextView list_error;

    @BindView(R.id.loading_view)
    ProgressBar loadingView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refresh;

    private ListViewModel viewModel;
    private CountryListAdapter adapter = new CountryListAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.refresh();
        countriesList.setLayoutManager(new LinearLayoutManager(this));
        countriesList.setAdapter(adapter);

        observerViewModel();
    }

    private void observerViewModel() {
        viewModel.countries.observe(this, countryModels -> {
            if (countryModels != null) {
                countriesList.setVisibility(View.VISIBLE);
                adapter.updateCountries(countryModels);
            }

        });

        viewModel.countryLoadError.observe(this, isError -> {
            if (isError != null) {
                list_error.setVisibility(isError ? View.VISIBLE : View.GONE);
            }

        });
        viewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    list_error.setVisibility(View.GONE);
                    countriesList.setVisibility(View.GONE);
                }
            }

        });

        refresh.setOnRefreshListener(() -> {
            viewModel.refresh();
            refresh.setRefreshing(false);
        });
    }
}
