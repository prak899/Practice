package com.example.practice;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.practice.databinding.ActivityCaptureMoveImageBinding;
import com.example.practice.model.MultipleResorces;
import com.example.practice.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class CaptureImage extends AppCompatActivity {
    public static String TAG = "CaptureImage";
    private MainViewModel viewModel;
    private ActivityCaptureMoveImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_move_image);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        getLiveData();
    }

    List<MultipleResorces.Datum> list = new ArrayList<>();

    void getLiveData() {
        // Observe LiveData to update the UI
        viewModel.getData().observe(this, data -> {
            if (null != data){
                for (MultipleResorces.Datum datum: data){
                    Log.d(TAG, "onResponse: class data" +
                            "\n" + datum.id +
                            "\n" + datum.first_name +
                            "\n" + datum.last_name +
                            "\n" + datum.email +
                            "\n" + datum.avatar);
                }
            }
        });
        viewModel.fetchUsersDetails();
    }

    private void goLive(){
        viewModel.getData().observe(this, new Observer<List<MultipleResorces.Datum>>() {
            @Override
            public void onChanged(List<MultipleResorces.Datum> data) {
                if (data != null) {
                    for (MultipleResorces.Datum datum : data) {
                        Log.d(TAG, "onResponse: class data" +
                                "\n" + datum.id +
                                "\n" + datum.first_name +
                                "\n" + datum.last_name +
                                "\n" + datum.email +
                                "\n" + datum.avatar);
                    }

                }
            }
        });
        viewModel.fetchUsersDetails();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}