package com.stud.awra.flickrappgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;

public class ActivityPhoto extends AppCompatActivity {

  private String photoUrl;
  private ImageView imageView;

  @Override protected void onStart() {
    super.onStart();
    Glide.with(this)
        .load(photoUrl)
        .placeholder(R.drawable.load)
        .error(R.drawable.error)
        .into(imageView);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    imageView = new ImageView(this);
    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));
    Intent intent = getIntent();
    photoUrl = intent.getStringExtra("photo");
    //photoUrl=savedInstanceState.getString("photo");
    setContentView(imageView);
  }
}
