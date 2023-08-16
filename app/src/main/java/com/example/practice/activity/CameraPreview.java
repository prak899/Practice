package com.example.practice.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.practice.R;
import com.example.practice.databinding.CameraPreviewBinding;

import java.io.File;

public class CameraPreview extends AppCompatActivity {

    private static final String TAG = "CameraPreview";
    CameraPreviewBinding binding;

    private String imagePath;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.camera_preview);

        imagePath = getIntent().getStringExtra("imagePath");
        Glide.with(this)
                .load(imagePath)
                .into(binding.previewImage);
        binding.imageClear.setOnClickListener(view -> imageDelete());
        binding.imageClearOk.setOnClickListener(view -> imageOk());
    }

    private void imageDelete() {
        File imageFileToDelete = new File(imagePath);

        if (imageFileToDelete.exists()) {
            boolean deleted = imageFileToDelete.delete();
            if (deleted) {
                startActivity(new Intent(this, CaptureImage.class));
            } else {
                Toast.makeText(this, "Unable to delete file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void imageOk() {
        startActivity(new Intent(this, FragmentContainer.class));
    }

}

