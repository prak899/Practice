package com.example.practice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.practice.R;
import com.example.practice.databinding.FragmentMultipleImagesBinding;
import com.example.practice.model.MultipleResorces;
import com.example.practice.retrofit.RetrofitClient;
import com.example.practice.viewmodel.MainViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultipleImages extends Fragment {
    private FragmentMultipleImagesBinding fragmentMultipleImagesBinding;
    private String valueOfTarget, cameraQuote, storageQuote, locationQuote;
    private SingleImage singleImage;
    Context mContext;

    private MainViewModel mainViewModel;
    public static String TAG ="captureImages";
    private Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This method is called when the fragment is created.
        // Initialize variables and perform setup here.
        context = getContext();
        valueOfTarget =
                "dgfhfdbfjhdfgdfgvdfkfdfsgfysfgusdkfgfgdfsdfbfjh";
        cameraQuote =
                "Please allow camera permission";
        storageQuote =
                "Please allow storage permission";
        locationQuote =
                "Please allow location permission";
        singleImage = new SingleImage();
        mContext = getContext();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // This method is called when the fragment's UI is being created.
        // Inflate the fragment's layout here.
        fragmentMultipleImagesBinding = FragmentMultipleImagesBinding.inflate(inflater, container, false);
        return fragmentMultipleImagesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // This method is called after the fragment's view has been created.
        // Perform any view-related setup here, such as finding views and setting click listeners.
        fragmentMultipleImagesBinding.agreeContinue.setOnClickListener(v->{
            switchFragment(singleImage);
        });
        getUserByPOJO();
        //getUserFromLivaData();
    }

    private void getUserFromLivaData() {
        mainViewModel.getData().observe(requireActivity(), data -> {
            if (null != data) {
                for (MultipleResorces.Datum datum : data) {
                    Log.d(TAG, "onResponse: class data" +
                            "\n" + datum.id +
                            "\n" + datum.first_name +
                            "\n" + datum.last_name +
                            "\n" + datum.email +
                            "\n" + datum.avatar);
                }
            }
        });
        mainViewModel.fetchUsersDetails();
    }

    @Override
    public void onStart() {
        super.onStart();
        // This method is called when the fragment becomes visible to the user.
        // It's a good place to perform UI updates that should be visible on the screen.
        fragmentMultipleImagesBinding.replaceableText.setText(valueOfTarget);
        fragmentMultipleImagesBinding.swCamera.setText(cameraQuote);
        fragmentMultipleImagesBinding.swStorage.setText(storageQuote);
        fragmentMultipleImagesBinding.swLocation.setText(locationQuote);
    }

    @Override
    public void onResume() {
        super.onResume();
        // This method is called when the fragment is about to interact with the user.
        // It's a good place to start animations, audio, or other resources.
    }

    @Override
    public void onPause() {
        super.onPause();
        // This method is called when the fragment is no longer interacting with the user.
        // Pause animations, audio, or other ongoing actions that may consume resources.
    }

    @Override
    public void onStop() {
        super.onStop();
        // This method is called when the fragment is no longer visible to the user.
        // Release resources or perform cleanup operations here.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // This method is called when the fragment's view is being destroyed.
        // Clean up any resources tied to the view here.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // This method is called when the fragment is being destroyed.
        // Perform final cleanup or release any remaining resources here.
        fragmentMultipleImagesBinding = null;
    }

    public void switchFragment(Fragment fragment){
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void getUsers() {
        ArrayList<Object> newUsers = new ArrayList<>();
        DataClass dataClass = new  DataClass();
        Log.d(TAG, "extracted: mehod");
        /*Call<JsonObject> call = RetrofitClient.getInstance().apiService()
                .getUsers();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (null != response.body() && response.code() == 200){
                    JsonArray jsonArray;
                    JsonObject jsonObject, jsonObject1;
                    jsonArray = (JsonArray) response.body().get("data");

                    for (int i = 0; i < jsonArray.size(); i++) {

                        newUsers.add(jsonArray);
                        jsonObject = (JsonObject) jsonArray.get(i).getAsJsonObject();
                        Log.d(TAG, "onResponse: data's" + jsonObject.get("id") +
                                "\n"+jsonObject.get("email")+
                                "\n"+jsonObject.get("first_name")+
                                "\n"+jsonObject.get("last_name")+
                                "\n"+jsonObject.get("avatar"));

                        *//*dataClass.setId(Integer.parseInt(String.valueOf(jsonObject.get("id"))));
                        dataClass.setEmail(String.valueOf(jsonObject.get("email")));
                        dataClass.setFirst_name(String.valueOf(jsonObject.get("first_name")));
                        dataClass.setLast_name(String.valueOf(jsonObject.get("last_name")));
                        dataClass.setAvatar(String.valueOf(jsonObject.get("avatar")));

                        Log.d(TAG, "onResponse: class data" +
                                "\n"+dataClass.id+
                                "\n"+dataClass.first_name+
                                "\n"+dataClass.last_name+
                                "\n"+dataClass.email+
                                "\n"+dataClass.avatar);*//*
                    }
                    jsonObject1 = (JsonObject) response.body().get("support");
                    String jsonData = jsonObject1.get("url") + "\n" + jsonObject1.get("text");
                    Log.d(TAG, "onResponse: "+jsonData);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });*/
    }
    private void getUserByPOJO(){
        Call<MultipleResorces.Root> rootCall = RetrofitClient
                .getInstance()
                .apiService()
                .getUsers();
        rootCall.enqueue(new Callback<MultipleResorces.Root>() {
            @Override
            public void onResponse(@NonNull Call<MultipleResorces.Root> call, @NonNull Response<MultipleResorces.Root> response) {
                if (200 == response.code() && null != response.body()){
                    ArrayList<MultipleResorces.Datum> datum = response.body().data;

                    for (MultipleResorces.Datum dataClass: datum){
                        Log.d(TAG, "onResponse: class data by POJO" +
                                "\n"+dataClass.id+
                                "\n"+dataClass.first_name+
                                "\n"+dataClass.last_name+
                                "\n"+dataClass.email+
                                "\n"+dataClass.avatar);
                        Log.d(TAG, "onResponse: "+rootCall.isExecuted());
                    }
                    MultipleResorces.Support support = response.body().support;
                    Log.d(TAG, "onResponse: support data from POJO"+"\n"+
                            support.text+"\n"+
                            support.url);

                }
            }

            @Override
            public void onFailure(@NonNull Call<MultipleResorces.Root> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
        if (rootCall.isExecuted()){
            Log.d(TAG, "getUserByPOJO: "+rootCall.toString());
        }
    }
}
class DataClass{
    public int id;
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;

    private ArrayList<Object> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<Object> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Object> users) {
        this.users = users;
    }
}
