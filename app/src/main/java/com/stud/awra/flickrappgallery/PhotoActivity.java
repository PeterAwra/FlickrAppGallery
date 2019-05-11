package com.stud.awra.flickrappgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {
  public static final String URL_IMAGE = "com.stud.awra.flickrappgallery.URL_IMAGE";
  private boolean visible = false;
  private FrameLayout appBar;
  private ImageView contentView;
  private String urlImage;
  private Photo photo;
  private TextView title;

  public static String getUrlImage(Photo photo, View view) {
    return String.format(
        "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg",
        photo.getFarm(),
        photo.getServer(),
        photo.getId(),
        photo.getSecret(),
        SizePhoto.getSize(view.getHeight(), view.getWidth()));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo);
    contentView = findViewById(R.id.fullscreen_content);
    appBar = findViewById(R.id.myapp_bar);
    findViewById(R.id.back_bt).setOnClickListener(this);
    findViewById(R.id.share_bt).setOnClickListener(this);
    title = findViewById(R.id.tv_title);
    SearchView searchView=findViewById(R.id.search_view);

    hide();
    load();
    contentView.setOnClickListener(v -> toggle());
  }

  private void load() {
    new Thread(() -> {
      while (contentView.getWidth() == 0) ;
      runOnUiThread(() -> {
        photo = (Photo) getIntent().getSerializableExtra(URL_IMAGE);
        urlImage =
            getUrlImage(photo, contentView);
        Glide.with(contentView.getContext()).load(urlImage).into(contentView);
        title.setText(photo.getTitle());
      });
    }).start();
  }

  private void toggle() {
    if (visible) {
      hide();
    } else {
      show();
    }
  }

  private void show() {
    appBar.setVisibility(View.VISIBLE);
    visible = true;
  }

  private void hide() {
    appBar.setVisibility(View.INVISIBLE);
    visible = false;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.back_bt: {
        onBackPressed();
        break;
      }
      case R.id.share_bt: {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, urlImage);
        share.putExtra(Intent.EXTRA_SUBJECT, photo.getTitle());
        startActivity(Intent.createChooser(share, "Share"));
        break;
      }
    }
  }
}
