package com.stud.awra.flickrappgallery;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

class PhotoHolder extends RecyclerView.ViewHolder {

  public static final String URL_IMAGE = "com.stud.awra.flickrappgallery.URL_IMAGE";
  private ImageView imageView;
  private Context context;

  public PhotoHolder(@NonNull final View itemView) {
    super(itemView);
    context = itemView.getContext();
    imageView = itemView.findViewById(R.id.im_view);
  }

  public void setData(Photo photo) {
    String photoUrl_icon = PhotoActivity.getUrlImage(photo,imageView);
    Glide.with(imageView.getContext()).load(photoUrl_icon).into(imageView);
    imageView.setOnClickListener(
        v -> context.startActivity(
            new Intent(context, PhotoActivity.class).putExtra(URL_IMAGE, photo)));
  }


}
