package jp.co.cyberagent.android.gpuimage;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static jp.co.cyberagent.android.gpuimage.util.TextureRotationUtil.TEXTURE_NO_ROTATION;

public class ImageBufferScaler {

    private float CUBE[];

    public ImageBufferScaler(float[] cube){

        pointFloatBuffer = ByteBuffer.allocateDirect(cube.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        textureFloatBuffer = ByteBuffer.allocateDirect(TEXTURE_NO_ROTATION.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        CUBE=cube;
    }

    public void ScaleImageBuffer() {


        ArrayFloatScaler arrayFloatScaler = new ArrayFloatScaler();
        arrayFloatScaler.setInputHeight(inputHeight);
        arrayFloatScaler.setInputWidth(inputWidth);
        arrayFloatScaler.setOutputHeight(outputHeight);
        arrayFloatScaler.setOutputWidth(outputWidth);
        arrayFloatScaler.setScaleType(scaleType);
        arrayFloatScaler.setRotation(rotation);

        pointFloatBuffer.clear();
        pointFloatBuffer.put(arrayFloatScaler.scaleFloatBuffer(CUBE)).position(0);

        arrayFloatFlipper bufferFlipper = new arrayFloatFlipper();
        bufferFlipper.setScaleType(scaleType);
        bufferFlipper.setRatioHeightMax(arrayFloatScaler.getRatioHeightMax());
        bufferFlipper.setRatioWidthMax(arrayFloatScaler.getRatioWidthMax());
        bufferFlipper.setRotation(rotation);
        bufferFlipper.setFlipHorizontal(flipHorizontal);
        bufferFlipper.setFlipVertical(flipHorizontal);

        textureFloatBuffer.clear();
        textureFloatBuffer.put(bufferFlipper.getBufferCoordinates()).position(0);

    }

    private boolean flipVertical=false;
    private boolean flipHorizontal=false;

    public void setFlipVertical(boolean flipVertical) {

        this.flipVertical = flipVertical;
    }

    public boolean getFlipVertical() {
        return flipVertical;
    }

    public void setFlipHorizontal(boolean flipHorizontal) {
        this.flipHorizontal = flipHorizontal;
    }

    public boolean getFlipHorizontal() {
        return flipHorizontal;
    }

    private int inputHeight;


    public void setInputHeight(int height) {

        this.inputHeight = height;
    }

    public int getInputHeight() {

        return inputHeight;
    }

    private int outputHeight;

    public void setOutputHeight(int height) {

        this.outputHeight = height;
    }

    public int getOutputHeight() {

        return outputHeight;

    }

    private int inputWidth;

    public void setInputWidth(int width) {

        inputWidth = width;
    }

    public int getInputWidth() {
        return inputWidth;
    }

    private int outputWidth;

    public void setOutputWidth(int width) {

        outputWidth = width;
    }

    public int getOutputWidth() {
        return outputWidth;
    }


    private GPUImage.ScaleType scaleType;

    public void setScaleType(GPUImage.ScaleType scaleType) {

        this.scaleType = scaleType;

    }

    public GPUImage.ScaleType getScaleType() {

        return scaleType;

    }

    private Rotation rotation;


    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }


    private FloatBuffer pointFloatBuffer;
    private FloatBuffer textureFloatBuffer;

    public FloatBuffer getPointFloatBuffer() {
        return pointFloatBuffer;
    }

    public void setPointFloatBuffer(FloatBuffer pointFloatBuffer) {
        this.pointFloatBuffer = pointFloatBuffer;
    }

    public FloatBuffer getTextureFloatBuffer() {
        return textureFloatBuffer;
    }

    public void setTextureFloatBuffer(FloatBuffer textureFloatBuffer) {
        this.textureFloatBuffer = textureFloatBuffer;
    }
}
