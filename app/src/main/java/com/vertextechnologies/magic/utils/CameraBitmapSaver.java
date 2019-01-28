package com.vertextechnologies.magic.utils;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.Log;

import jp.co.cyberagent.android.gpuimage.GPUImage;

public class CameraBitmapSaver {

    private GPUImage gpuImage;

    private CameraLoader cameraLoader;

    public CameraBitmapSaver(CameraLoader cameraLoader,GPUImage gpuImage){

        this.cameraLoader = cameraLoader;

        this.gpuImage = gpuImage;

    }

    public void takePicture(Camera.PictureCallback callback) {

        Camera.Parameters params = cameraLoader.getCameraInstannce().getParameters();
        params.setRotation(90);

        cameraLoader.getCameraInstannce().setParameters(params);

        for (Camera.Size size : params.getSupportedPictureSizes()) {

            Log.i("ASDF", "Supported: " + size.width + "x" + size.height);

        }

        cameraLoader.getCameraInstannce().takePicture(null, null,callback);

    }

}
