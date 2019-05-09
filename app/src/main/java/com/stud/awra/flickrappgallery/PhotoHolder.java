package com.stud.awra.flickrappgallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class PhotoHolder extends RecyclerView.ViewHolder {

  private final TextView textView;

  public PhotoHolder(@NonNull View itemView) {
    super(itemView);
    textView = itemView.findViewById(R.id.tv);
  }

  public void setData(Photo photo) {
    textView.setText(photo.getTitle());
  }
}
