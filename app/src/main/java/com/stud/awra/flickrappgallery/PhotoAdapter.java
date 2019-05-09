package com.stud.awra.flickrappgallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
  List<Photo> photos;

  public PhotoAdapter() {
    photos = new ArrayList<>();
  }

  @NonNull @Override
  public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View inflate = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.holder_photo_layout, viewGroup, false);
    return new PhotoHolder(inflate);
  }

  @Override public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int i) {
    photoHolder.setData(photos.get(i));
  }

  @Override public int getItemCount() {
    return photos.size();
  }

  public void setData(List<Photo> photos) {
    this.photos.clear();
    this.photos.addAll(photos);
    notifyDataSetChanged();
  }
}
