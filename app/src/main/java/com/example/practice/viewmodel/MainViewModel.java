package com.example.practice.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.practice.model.MultipleResorces;
import com.example.practice.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainViewModel extends ViewModel {
    private final MutableLiveData<List<MultipleResorces.Datum>> data = new MutableLiveData<>();
    public LiveData<List<MultipleResorces.Datum>> getData() {
        return data;
    }
    private static final String TAG = "viewModel";
    public void fetchUsersDetails() {
        new Thread(() -> {
            //UI thread for this
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Replace this with your actual network call
            Call<MultipleResorces.Root> liveDataCall = RetrofitClient.getInstance().apiService().getUsers();
            liveDataCall.enqueue(new Callback<MultipleResorces.Root>() {
                @Override
                public void onResponse(@NonNull Call<MultipleResorces.Root> call, @NonNull Response<MultipleResorces.Root> response) {
                    if (null != response.body() && 200 == response.code()){
                        MultipleResorces.Root root = response.body();
                        List<MultipleResorces.Datum> datumList = root.data;

                        data.postValue(datumList);
                    }
                }
                @Override
                public void onFailure(@NonNull Call<MultipleResorces.Root> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure: "+ t.getMessage());
                }
            });
        }).start();
    }
}
