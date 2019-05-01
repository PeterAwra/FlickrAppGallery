package com.stud.awra.flickrappgallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
  private MainActivity mainActivity;
  private List<Photo> data = new ArrayList<>();

  public PhotoAdapter(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }

  @NonNull @Override
  public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    Context context = viewGroup.getContext();
    View view = LayoutInflater.from(context).inflate(R.layout.photo_item, viewGroup, false);
    return new PhotoHolder(view);
  }

  @Override public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int i) {
    photoHolder.setData(data.get(i));
  }

  @Override public int getItemCount() {
    return data.size();
  }

  public void setData(List<Photo> photos) {
    data.clear();
    if (photos != null) {
      data.addAll(photos);
    } else {
      Toast.makeText(mainActivity, "a", Toast.LENGTH_SHORT).show();
    }
    notifyDataSetChanged();
  }
}
