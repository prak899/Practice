package com.example.practice.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.practice.R;
import com.example.practice.databinding.FragmentSingleImageBinding;
import com.example.practice.utils.FileManager;

import java.util.List;

public class SingleImage extends Fragment {

    private static final String TAG = "SingleImage";
    private FragmentSingleImageBinding fragmentSingleImageBinding;
    Context mContext;
    private String changeableText, yourName;
    private StringBuilder stringBuilder;
    FileManager fileManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This method is called when the fragment is created.
        // Initialize variables and perform setup here.
        mContext = getActivity();
        stringBuilder = new StringBuilder();
        yourName = "Prakash Majhi";
        changeableText = String.valueOf(stringBuilder.append("Dear, ")
                        .append(yourName)
                        .append("Your text has been changed"));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // This method is called when the fragment's UI is being created.
        // Inflate the fragment's layout here.
        fragmentSingleImageBinding = FragmentSingleImageBinding.inflate(inflater, container, false);
//        extracted();
        return fragmentSingleImageBinding.getRoot();
    }

    private void extracted() {
        FileManager fileManager = new FileManager(requireContext());
            List<String> fileNames = fileManager.getAllImageFileNames();
            for (String fileName : fileNames) {
                Log.d(TAG, "onViewCreated: " + fileName);
                if (!fileName.isEmpty()) {
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background) // Optional placeholder image
                            .error(R.drawable.ic_launcher_foreground); // Optional error image

                    //FileManager.path+ "/" +
                    Glide.with(requireActivity())
                            .load(Uri.parse(fileName))
                            .apply(requestOptions)
                            .into(fragmentSingleImageBinding.localImages);
                } else {
                    Toast.makeText(mContext, "Unable to load images", Toast.LENGTH_SHORT).show();
                }
            }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // This method is called after the fragment's view has been created.
        // Perform any view-related setup here, such as finding views and setting click listeners.
        fragmentSingleImageBinding.next.setOnClickListener(v->{
            if (null != changeableText) {
                fragmentSingleImageBinding.replaceableText.setText(changeableText);
            } else {
                Toast.makeText(mContext, "No data", Toast.LENGTH_SHORT).show();
            }
        });
        /*Glide.with(this)
                .load(fileManager.loadImageFile())
                .into(imageView);*/
        extracted();
    }

    @Override
    public void onStart() {
        super.onStart();
        // This method is called when the fragment becomes visible to the user.
        // It's a good place to perform UI updates that should be visible on the screen.
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
        fragmentSingleImageBinding = null;
    }
}