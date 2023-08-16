package com.example.practice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.practice.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<String> imagePaths;

    public ImageAdapter(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imagePath = imagePaths.get(position);
        Glide.with(holder.itemView.getContext())
                .load(imagePath)
                .into(holder.imageView);
        /*Glide.with(holder.itemView.getContext())
                .load(imagePath)
                .into(holder.imageViewRight);

        holder.itemView.setOnClickListener(v -> {
            // Launch ImagePreviewActivity with the clicked image path
            Intent intent = new Intent(holder.itemView.getContext(), CameraPreview.class);
            intent.putExtra("imagePath", imagePath);
            holder.itemView.getContext().startActivity(intent);
        });*/

    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, imageViewRight;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageViewRight = itemView.findViewById(R.id.imageViewRight);
        }
    }
}
