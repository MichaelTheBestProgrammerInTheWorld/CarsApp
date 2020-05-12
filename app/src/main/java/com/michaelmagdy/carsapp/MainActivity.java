package com.michaelmagdy.carsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.michaelmagdy.carsapp.adapter.CarsListAdapter;
import com.michaelmagdy.carsapp.webservice.ApiClient;
import com.michaelmagdy.carsapp.webservice.ApiInterface;
import com.michaelmagdy.carsapp.webservice.DataItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView carsRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ApiInterface apiInterface;
    CarsListAdapter adapter;
    List<DataItem> dataList = new ArrayList<>();
    int page = 1;
    String brand, constructionYear, imageUrl;
    boolean isUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carsRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new CarsListAdapter(this, dataList);
        carsRecyclerView.setLayoutManager(layoutManager);
        carsRecyclerView.setAdapter(adapter);
        getCarsRequest();
        carsRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                getCarsRequest();
            }
        });

        mSwipeRefreshLayout =  findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCarsRequest();
                Toast.makeText(MainActivity.this, "Refreshed Successfully", Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getCarsRequest () {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<com.michaelmagdy.carsapp.webservice.Response> call = apiInterface.getCars("cars?page=" + page);
        call.enqueue(new Callback<com.michaelmagdy.carsapp.webservice.Response>() {
            @Override
            public void onResponse(Call<com.michaelmagdy.carsapp.webservice.Response> call,
                                   Response<com.michaelmagdy.carsapp.webservice.Response> response) {
                com.michaelmagdy.carsapp.webservice.Response result = response.body();
                if(result != null) {
                    int status = result.getStatus();
                    if(status == 1) {
                        List<DataItem> d = result.getData();

                        for (int i = 0; i < d.size(); i++) {
                            DataItem data = new DataItem();

                            brand = d.get(i).getBrand();
                            constructionYear = d.get(i).getConstructionYear();
                            imageUrl = d.get(i).getImageUrl();
                            isUsed = d.get(i).isIsUsed();


                            dataList.add(data);
                        }

                        page++;

                        adapter.notifyDataSetChanged();
                        adapter.notifyItemRangeInserted(adapter.getItemCount(), dataList.size() - 1);


                    } else {
                        Toast.makeText(MainActivity.this, "End of List", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<com.michaelmagdy.carsapp.webservice.Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
