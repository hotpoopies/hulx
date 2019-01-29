/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vertextechnologies.magic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.media.ExifInterface;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.vertextechnologies.magic.utils.CameraBitmapSaver;
import com.vertextechnologies.magic.utils.CameraHelper;
import com.vertextechnologies.magic.utils.CameraLoader;
import com.vertextechnologies.magic.utils.CameraPictureCallback;
import com.vertextechnologies.magic.utils.PickColorFromCameraPreviewRendererCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImage.OnPictureSavedListener;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;

public class ActivityCamera extends Activity implements OnSeekBarChangeListener, OnClickListener, View.OnTouchListener {

    private GPUImage mGPUImage;
    private CameraHelper mCameraHelper;
    private CameraLoader mCamera;
    private GPUImageFilter mFilter;
    private GPUImageFilter mSelectedFilter;
    private GPUImageFilterTools.FilterAdjuster mFilterAdjuster;
    private Bitmap background;
    private SeekBar seekBar;

    private TextView textViewContrastValue;
    private TextView textViewBrightnessValue;
    private TextView textViewZoomValue;
    private TextView textViewBackgroundContrastValue;
    private TextView textViewBackgroundBrightnessValue;
    private TextView textViewBackgroundZoomValue;

    private int contrastValue=50;
    private int brightnessValue=50;
    private int zoomValue=50;
    private int backgroundContrastValue=50;
    private int backgroundBrightnessValue=50;
    private int backgroundZoomValue=50;

    private String filterTypeField="";

    private GPUImageFilterGroup filterGroup;

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        ((SeekBar) findViewById(R.id.seekBar)).setOnSeekBarChangeListener(this);
        findViewById(R.id.button_choose_filter).setOnClickListener(this);
        findViewById(R.id.button_choose_background).setOnClickListener(this);
        findViewById(R.id.button_capture).setOnClickListener(this);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        mGPUImage = new GPUImage(this);

        GLSurfaceView surfaceView = (GLSurfaceView) findViewById(R.id.surfaceView);
        mGPUImage.setGLSurfaceView(surfaceView);
        surfaceView.setOnTouchListener(this);

        mCameraHelper = new CameraHelper(this);
        mCamera = new CameraLoader(mCameraHelper,mGPUImage,this);

        textViewContrastValue=(TextView) findViewById(R.id.textViewContrastValue);
        textViewBrightnessValue=(TextView) findViewById(R.id.textViewBrightnessValue);
        textViewZoomValue=(TextView) findViewById(R.id.textViewZoomValue);
        textViewBackgroundContrastValue=(TextView) findViewById(R.id.textViewBackgroundContrastValue);
        textViewBackgroundBrightnessValue=(TextView) findViewById(R.id.textViewBackgroundBrightnessValue);
        textViewBackgroundZoomValue=(TextView) findViewById(R.id.textViewBackgroundZoomValue);

        View cameraSwitchView = findViewById(R.id.img_switch_camera);
        cameraSwitchView.setOnClickListener(this);

        if (!mCameraHelper.hasFrontCamera() || !mCameraHelper.hasBackCamera()) {

            cameraSwitchView.setVisibility(View.GONE);

        }

        List<GPUImageFilter> filters = new LinkedList<GPUImageFilter>();

        filterGroup = new GPUImageFilterGroup(filters);

        switchFilterTo(
                GPUImageFilterTools.createBlendFilter(
                        this, GPUImageChromaKeyBlendFilter.class,
                        null)
        );

        seekBar.setProgress(50);

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(ActivityCamera.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ActivityCamera.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera.onResume();
    }

    @Override
    protected void onPause() {
        mCamera.onPause();
        super.onPause();
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.button_choose_filter:
                GPUImageFilterTools.showDialog(this, new GPUImageFilterTools.OnGpuImageFilterChosenListener() {

                    @Override
                    public void onGpuImageFilterChosenListener(GPUImageFilter filter, String filterType) {

                        filterTypeField = filterType;
                        switch (filterType){

                            case "Contrast Camera":

                                filterGroup.setSelectedFiltersIndex(0);
                                seekBar.setProgress(contrastValue);

                                break;

                            case "Brightness Camera":

                                filterGroup.setSelectedFiltersIndex(1);
                                seekBar.setProgress(brightnessValue);
                                break;

                            case "Zoom Camera":

                                filterGroup.setSelectedFiltersIndex(2);
                                seekBar.setProgress(zoomValue);
                                break;

                            case "Contrast Background":

                                filterGroup.setSelectedFiltersIndex(3);
                                seekBar.setProgress(backgroundContrastValue);
                                break;

                            case "Brightness Background":

                                filterGroup.setSelectedFiltersIndex(4);
                                seekBar.setProgress(backgroundBrightnessValue);
                                break;

                            case "Zoom Background":

                                filterGroup.setSelectedFiltersIndex(5);

                                seekBar.setProgress(backgroundZoomValue);
                                break;

                            case "Change Background":




                                break;
                        }

                    }

                });
                break;

            case R.id.button_capture:
                if (mCamera.getCameraInstannce().getParameters().getFocusMode().equals(
                        Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    takePicture();
                } else {
                    mCamera.getCameraInstannce().autoFocus(new Camera.AutoFocusCallback() {

                        @Override
                        public void onAutoFocus(final boolean success, final Camera camera) {
                            takePicture();
                        }
                    });
                }
                break;

            case R.id.img_switch_camera:
                mCamera.switchCamera();
                break;

            case R.id.button_choose_background:
                filterGroup.setSelectedFiltersIndex(6);

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_PICK_IMAGE);

                break;

        }
    }

    private void takePicture() {

        CameraBitmapSaver cameraBitmapSaver = new CameraBitmapSaver(mCamera,mGPUImage);

        CameraPictureCallback cameraPictureCallback =
                new CameraPictureCallback(
                        mGPUImage,mCamera.getCameraInstannce(),
                        (GLSurfaceView) findViewById(R.id.surfaceView));

        cameraBitmapSaver.takePicture(cameraPictureCallback);

    }

    private void   switchFilterTo(final GPUImageFilter filter) {
       // if (mFilter == null
       //         || (filter != null && !mFilter.getClass().equals(filter.getClass()))) {
            mFilter = filter;
            mGPUImage.setFilter(mFilter);
            mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(mFilter, getApplicationContext());
       // }
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress,
                                  final boolean fromUser) {

        if (mFilterAdjuster != null) {
            mFilterAdjuster.adjust(progress);
        }

        switch (filterTypeField) {

            case "Contrast Camera":
                contrastValue = progress;

                textViewContrastValue.setText("Camera Contrast : " +  String.valueOf(progress));
                break;

            case "Brightness Camera":

                textViewContrastValue.setText("Camera Brightness : " +  String.valueOf(progress));
                brightnessValue = progress;
                break;

            case "Zoom Camera":

                textViewZoomValue.setText("Camera Zoom : " +  String.valueOf(progress));
                zoomValue = progress;


                break;

            case "Contrast Background":

                textViewContrastValue.setText("Background Contrast : " +  String.valueOf(progress));
                backgroundContrastValue = progress;
                break;

            case "Brightness Background":

                textViewContrastValue.setText("Background Brightness : " +  String.valueOf(progress));
                backgroundBrightnessValue = progress;
                break;

            case "Zoom Background":

                textViewContrastValue.setText("Background Zoom : " +  String.valueOf(progress));
                backgroundZoomValue = progress;
                break;

            case "Change Background":


                break;
        }



    }


    private static final int REQUEST_PICK_IMAGE = 1;


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_PICK_IMAGE:
                if (resultCode == RESULT_OK) {

                    Bitmap bm=null;
                    if (data != null) {
                        try {
                            bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                           if(isStoragePermissionGranted()) {

                               bm = ActivityCamera.modifyOrientation(bm, getRealPathFromURI(getApplicationContext(), data.getData()));

                           }

                           List<GPUImageFilter> filters = new LinkedList<GPUImageFilter>();
                           filters.add(new GPUImageContrastFilter());
                           filters.add(new GPUImageBrightnessFilter());

                           filters.add(new GPUImageTransformFilter());

                           filters.add(GPUImageFilterTools.createBlendFilter(this, GPUImageChromaKeyBlendFilter.class,bm));

                           filterGroup = new GPUImageFilterGroup(filters);
                           switchFilterTo( filterGroup);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    finish();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
        }
    }
    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {


        if (mCamera.getCameraInstannce().getParameters().getFocusMode().equals(
                Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            CameraBitmapSaver cameraBitmapSaver = new CameraBitmapSaver(mCamera,mGPUImage);
            PickColorFromCameraPreviewRendererCallback pickColorFromCameraPreviewRendererCallback =
                    new PickColorFromCameraPreviewRendererCallback(
                            mGPUImage,mCamera.getCameraInstannce(),
                            (GLSurfaceView) findViewById(R.id.surfaceView),mGPUImage.getmRenderer(),event.getRawX(),event.getRawY());

            cameraBitmapSaver.takePicture(pickColorFromCameraPreviewRendererCallback);

        } else {
            mCamera.getCameraInstannce().autoFocus(new Camera.AutoFocusCallback() {

                @Override
                public void onAutoFocus(final boolean success, final Camera camera) {
                    takePicture();
                }
            });
        }

        return false;
    }
}
