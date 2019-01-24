package jp.co.cyberagent.android.gpuimage;

import java.nio.FloatBuffer;


public class ImageBufferModifyParameters {

    private float outputHeight;

    public float getOutputHeight() {

        return outputHeight;

    }

    public void setOutputHeight(float outputHeight) {

        this.outputHeight = outputHeight;

    }

    private Rotation rotation;

    public Rotation getRotation() {

        return rotation;

    }

    public void setRotation(Rotation rotation) {

        this.rotation = rotation;

    }

    private float outputWidth;

    public float getOutputWidth() {

        return outputWidth;

    }

    public void setOutputWidth(float outputWidth) {

        this.outputWidth = outputWidth;

    }

    private float imageWidth;

    public float getImageWidth() {

        return imageWidth;

    }

    public void setImageWidth(float imageWidth) {

        this.imageWidth = imageWidth;

    }

    private float[] CUBE;

    public float[] getCUBE() {

        return CUBE;

    }

    public void setCUBE(float[] cube) {

        this.CUBE = cube;

    }

    private boolean flipHorizontal;

    public boolean getFlipHorizontal() {

        return flipHorizontal;

    }

    public void setFlipHorizontal(boolean flipHorizontal) {

        this.flipHorizontal = flipHorizontal;

    }

    private float imageHeight;

    public float getImageHeight() {

        return imageHeight;

    }

    public void setImageHeight(float imageHeight) {

        this.imageHeight = imageHeight;

    }

    private boolean flipVertical;

    public boolean getFlipVertical() {

        return flipVertical;

    }

    public void setFlipVertical(boolean flipVertical) {

        this.flipVertical = flipVertical;

    }

    private GPUImage.ScaleType scaleType;

    public GPUImage.ScaleType getScaleType() {

        return scaleType;

    }

    public void setScaleType(GPUImage.ScaleType scaleType) {

        this.scaleType = scaleType;

    }

    private FloatBuffer GLCubeBuffer;

    public FloatBuffer getGLCubeBuffer() {

        return GLCubeBuffer;

    }

    public void setGLCubeBuffer(FloatBuffer glCubeBuffer) {

        this.GLCubeBuffer = glCubeBuffer;

    }

    private FloatBuffer GLTextureBuffer;

    public FloatBuffer getGLTextureBuffer() {

        return GLTextureBuffer;

    }

    public void setGLTextureBuffer(FloatBuffer glTextureBuffer) {

        this.GLTextureBuffer = glTextureBuffer;

    }

}
