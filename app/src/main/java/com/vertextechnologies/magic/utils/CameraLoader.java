package com.vertextechnologies.magic.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;

import com.vertextechnologies.magic.ActivityCamera;

import jp.co.cyberagent.android.gpuimage.GPUImage;

public class CameraLoader {

    private final Context context;
    private GPUImage gpuImage;
    private int mCurrentCameraId = 0;
    private Camera mCameraInstance;
    private CameraHelper cameraHelper;


    public android.hardware.Camera getCameraInstannce(){

        return  mCameraInstance;

    }

    public CameraLoader(CameraHelper cameraHelper, GPUImage gpuImage, Context context){

        this.cameraHelper = cameraHelper;

        this.gpuImage = gpuImage;

        this.context = context;

    }

    public void onResume() {

        setUpCamera(mCurrentCameraId);

    }

    public void onPause() {

        releaseCamera();

    }

    public void switchCamera() {

        releaseCamera();

        mCurrentCameraId = (mCurrentCameraId + 1) % cameraHelper.getNumberOfCameras();

        setUpCamera(mCurrentCameraId);

    }

    private void setUpCamera(final int id) {

        mCameraInstance = getCameraInstance(id);
        Camera.Parameters parameters = mCameraInstance.getParameters();

        if (parameters.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        mCameraInstance.setParameters(parameters);

        int orientation = cameraHelper.getCameraDisplayOrientation((Activity) context, mCurrentCameraId);
        CameraHelper.CameraInfo2 cameraInfo = new CameraHelper.CameraInfo2();
        cameraHelper.getCameraInfo(mCurrentCameraId, cameraInfo);
        boolean flipHorizontal = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT;
        gpuImage.setUpCamera(mCameraInstance, orientation, flipHorizontal, false);

    }

    /** A safe way to get an instance of the Camera object. */
    private Camera getCameraInstance(final int id) {

        Camera c = null;

        try {

            c = cameraHelper.openCamera(id);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return c;

    }

    private void releaseCamera() {
        mCameraInstance.setPreviewCallback(null);
        mCameraInstance.release();
        mCameraInstance = null;
    }
}
