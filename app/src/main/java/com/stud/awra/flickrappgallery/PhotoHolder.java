package com.stud.awra.flickrappgallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
  private final TextView tv;
  private final ImageView imageView;
  private final Context context;
  private String photoUrl;
  private String photoUrl_icon;

  public PhotoHolder(@NonNull View itemView) {
    super(itemView);
    itemView.setOnClickListener(this);
    tv = itemView.findViewById(R.id.tv_holder);
    imageView = itemView.findViewById(R.id.iv);
    context = itemView.getContext();
  }

  public void setData(Photo photo) {
    photoUrl_icon = String.format(
        "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg",
        photo.getFarm(),
        photo.getServer(),
        photo.getId(),
        photo.getSecret(),
        "m");
    photoUrl = String.format(
        "https://farm%s.staticflickr.com/%s/%s_%s.jpg",
        photo.getFarm(),
        photo.getServer(),
        photo.getId(),
        photo.getSecret()
    );
    Glide.with(context)
        .load(photoUrl_icon)
        .placeholder(R.drawable.load)
        .error(R.drawable.error)
        .into(imageView);
  }

  @Override public void onClick(View v) {
    Intent intent = new Intent(context, ActivityPhoto.class);
    intent.putExtra("photo", photoUrl);
    Bundle options = new Bundle();
    options.putString("photo", photoUrl);
    context.startActivity(intent, options);
  }
}
