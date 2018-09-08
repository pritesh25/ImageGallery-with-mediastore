package com.selfie.life.imagegallery;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Pawan on 4/8/2016.
 */
public class ImageMediaQuery {

    private static final String TAG = ImageMediaQuery.class.getSimpleName();
    private Context context;
    private int count = 0;
    private Cursor cursor;
    ArrayList<ImageItem> imageItems;
    public ImageMediaQuery(Context context){
        this.context=context;
    }

    public ArrayList<ImageItem> getAllVideo() {

        String selection = null;

        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_ADDED,
        };


        cursor = context.getContentResolver().query(

                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Images.Media.DATE_ADDED+" DESC");
                //MediaStore.Images.Media.DATE_ADDED+" DESC LIMIT 1");

        imageItems = new ArrayList<ImageItem>();
        ImageItem imageItem;
        while (cursor.moveToNext()) {

            imageItem = new ImageItem();
            imageItem.set_ID(cursor.getString(0));
            imageItem.setTITLE(cursor.getString(1));
            imageItem.setDATA(cursor.getString(2));
            imageItem.setDISPLAY_NAME(cursor.getString(3));
            imageItem.setSIZE(cursor.getString(4));
            imageItem.setSIZE(cursor.getString(5));

            Log.d(TAG,"*******************************************");
            Log.d(TAG,"image id           = "+cursor.getString(0));
            Log.d(TAG,"image title        = "+cursor.getString(1));
            Log.d(TAG,"image data         = "+cursor.getString(2));
            Log.d(TAG,"image display name = "+cursor.getString(3));
            Log.d(TAG,"image size         = "+cursor.getString(4));
            Log.d(TAG,"image date         = "+cursor.getString(5));

            imageItems.add(imageItem);

        }
    return imageItems;
    }

    public int getVideoCount(){
        int count=0;
        count=(getAllVideo()).size();
        return count;

    }

    private boolean FileExistance(String path)
    {
        boolean isExist = false;
        File file       = new File(path);

        if(file.exists())
        {
            isExist = true;
        }
        else
        {
            isExist = false;
        }
        return isExist;
    }

}
