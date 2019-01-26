package jp.co.cyberagent.android.gpuimage;


public class ArrayFloatScaler {
    
    private float ratio;


    public float getRatio(){

        return  ratio;
    }

    public float[] scaleFloatBuffer(float[] floatBuffer) {

        float[] scaleFloatBufferArray = new float[floatBuffer.length];

        scaleFloatBufferArray = floatBuffer;

        if (rotation == Rotation.ROTATION_270 || rotation == Rotation.ROTATION_90) {

            int temp;
            temp = outputWidth;
            outputWidth = outputHeight;
            outputHeight = temp;
        }


        float outputWidthCast = outputWidth;
        float outputHeightCast = outputHeight;

        float ratioWidth = outputWidthCast / inputWidth;
        float ratioHeight = outputHeightCast / inputHeight;

        ratio = Math.max(ratioWidth, ratioHeight);

        int imageWidthNew = Math.round(inputWidth * ratio);
        int imageHeightNew = Math.round(inputHeight * ratio);

        ratioWidthMax = imageWidthNew / outputWidthCast;
        ratioHeightMax = imageHeightNew / outputHeightCast;


        if (scaleType != GPUImage.ScaleType.CENTER_CROP) {

            scaleFloatBufferArray = new float[]{
                    floatBuffer[0] / ratioHeightMax, floatBuffer[1] / ratioWidthMax,
                    floatBuffer[2] / ratioHeightMax, floatBuffer[3] / ratioWidthMax,
                    floatBuffer[4] / ratioHeightMax, floatBuffer[5] / ratioWidthMax,
                    floatBuffer[6] / ratioHeightMax, floatBuffer[7] / ratioWidthMax,
            };

        } else {

            scaleFloatBufferArray = new float[]{
                    floatBuffer[0], floatBuffer[1],
                    floatBuffer[2], floatBuffer[3],
                    floatBuffer[4], floatBuffer[5],
                    floatBuffer[6], floatBuffer[7],
            };

        }


        return scaleFloatBufferArray;

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


    private float ratioWidthMax;

    public float getRatioWidthMax() {
        return ratioWidthMax;
    }

    public void setRatioWidthMax(float ratioWidth) {
        this.ratioWidthMax = ratioWidth;
    }

    private float ratioHeightMax;

    public float getRatioHeightMax() {
        return ratioHeightMax;
    }

    public void setRatioHeightMax(float ratioHeight) {
        this.ratioHeightMax = ratioHeight;
    }
}
