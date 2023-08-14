package com.example.practice.utils;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private Context context;
    public static final String path = "file:///storage/emulated/0/Android/data/com.example.practice/files/Pictures/NarwaMissionProject";
    public FileManager(Context context) {
        this.context = context;
    }
    public File loadImageFile(String fileName) {
        return new File(context.getFilesDir(), fileName);
    }
    public List<String> getAllImageFileNames() {
        List<String> imageFileNames = new ArrayList<>();
        File[] files = context.getFilesDir().listFiles();
        File[] files1 = new File(path).listFiles();
        if (files1 != null) {
            for (File file : files1) {
                if (file.isFile() && isImageFile(file.getName())) {
                    imageFileNames.add(file.getName());
                }
            }
        }
        return imageFileNames;
    }

    // Method to check if a file has an image extension (adjust this based on your needs)
    private boolean isImageFile(String fileName) {
        return fileName.toLowerCase().endsWith(".jpg") ||
                fileName.toLowerCase().endsWith(".jpeg") ||
                fileName.toLowerCase().endsWith(".png") ||
                fileName.toLowerCase().endsWith(".gif");
        // Add more image extensions if needed
    }
}
