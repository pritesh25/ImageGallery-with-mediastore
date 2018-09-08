package com.selfie.life.imagegallery;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.selfie.life.imagegallery.API.UtilityRecyclerViewColumn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageGalleryFragment extends Fragment implements ImageAdapter.AdapterCallback{

    private static final String TAG = ImageGalleryFragment.class.getSimpleName();
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private ImageMediaQuery imageMediaQuery;
    private ArrayList<ImageItem> imageItemList;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    Activity activity;
    private RecyclerView.LayoutManager      mLayoutManager;

    private Callback mCallback;

    public ImageGalleryFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ImageGalleryFragment(MainImageGalleryActivity mainImageGalleryActivity) {

        mCallback = mainImageGalleryActivity;

    }

    public static ImageGalleryFragment newInstance() {
        ImageGalleryFragment fragment = new ImageGalleryFragment();
        return fragment;
    }

    @Override
    public void onAdapterImageSelected(String path) {

        //mCallback = (Callback) getFragmentManager().findFragmentByTag(TAG);

        Log.d(TAG,"Video Path = "+path);
        mCallback.onSingleImageSelected(path);
        getActivity().finish();
    }

    public interface Callback {
        void onSingleImageSelected(String path);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkandAskPermission();
        } else {
            initVideo();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.image_gallery_fragment, container, false);
    }

    private void initVideo() {
        recyclerView= (RecyclerView)getActivity().findViewById(R.id.recyclerView);
        imageItemList =new ArrayList<ImageItem>();
        imageMediaQuery =new ImageMediaQuery(getContext());
        imageItemList = imageMediaQuery.getAllVideo();
        Log.d(TAG,"Array      :"+ imageItemList);
        Log.d(TAG,"Array Count:"+ imageItemList.size());

        int mNoOfColumns    = UtilityRecyclerViewColumn.calculateNoOfColumns(getContext());
        mLayoutManager      = new GridLayoutManager(getActivity(), mNoOfColumns);

        imageAdapter =new ImageAdapter(imageItemList,getActivity(),"Gallery",true,false,activity,ImageGalleryFragment.this);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                switch (imageAdapter.getItemViewType(position)) {
                    case 0:
                        return 2;
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    default:
                        return 1;
                }

            }
        });
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(imageAdapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    initVideo();
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private void checkandAskPermission() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 0; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }

            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
        initVideo();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

}
