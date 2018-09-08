package com.selfie.life.imagegallery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Pawan on 2/20/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ImageAdapter.class.getSimpleName();
    Context context;
    Activity activity;
    String name;
    Bundle bundle=new Bundle();
    private List<ImageItem> videoList;

    AdapterCallback mAdapterCallback;

    public ImageAdapter(List<ImageItem> videoList, Context context, String album, boolean withHeader, boolean withFooter, Activity activity, ImageGalleryFragment mAdapterCallback) {
        this.videoList = videoList;
        this.context=context;
        this.activity = activity;
        this.mAdapterCallback = mAdapterCallback;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_gallery_item, viewGroup, false);
        return new VideoViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ImageItem mediaObject=videoList.get(position);

        //set name
//        name=mediaObject.getDISPLAY_NAME();
//        if (name.length() > 25) {
//            ((VideoViewHolder) holder).vName.setText(name.substring(0, 25) + "..");
//        } else {
//            ((VideoViewHolder) holder).vName.setText(name);
//        }

        //set value to view
        //((VideoViewHolder) holder).vImage.setImageResource(R.color.w);
        ((VideoViewHolder) holder).vFilePath    = mediaObject.getDATA();
//        ((VideoViewHolder) holder).context      = context;
//        ((VideoViewHolder) holder).b            = bundle;
//        ((VideoViewHolder) holder).position     = position;

        //log print
        Log.d(TAG,"video data           = "+mediaObject.getDATA());
        Log.d(TAG,"video size (byte)    = "+mediaObject.getSIZE());
        Log.d(TAG,"video date           = "+mediaObject.getDATE());

        //set preview images
        Glide.with(context)
                .load(mediaObject.getDATA())
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .crossFade()
                .into(((VideoViewHolder) holder).vImage);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        private ImageView vImage;
        private String vFilePath;

        public VideoViewHolder(View v) {
            super(v);
            vImage = (ImageView)  v.findViewById(R.id.media_img_bck);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"Video Path = "+(vFilePath));
                    mAdapterCallback.onAdapterImageSelected(vFilePath);
                }
            });
        }
    }

    public interface AdapterCallback {
        void onAdapterImageSelected(String path);
    }

}
