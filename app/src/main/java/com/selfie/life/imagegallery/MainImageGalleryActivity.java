package com.selfie.life.imagegallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class MainImageGalleryActivity extends AppCompatActivity implements ImageGalleryFragment.Callback
{
    private static final String TAG = MainImageGalleryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        getSupportActionBar().hide();
        setGalleryFragment();
    }

    public void setGalleryFragment(){
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        Fragment galleryFragment= new ImageGalleryFragment(this);
        ft.replace(R.id.main_container,galleryFragment);
        ft.commit();
    }

    @Override
    public void onSingleImageSelected(String path) {

//        Log.d(TAG,"Video Path = "+path);
//        Intent data = new Intent();
//        data.putExtra("MESSAGE", path);
//        setResult(ChatMessageFragment.REQUEST_GALLERY_VIDEO, data);
//        finish();
    }

}