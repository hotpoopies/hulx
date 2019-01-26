package jp.co.cyberagent.android.gpuimage;


public class arrayFloatFlipper {


    public static final float TEXTURE_NO_ROTATION[] = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
    };

    public static final float TEXTURE_ROTATED_90[] = {
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            0.0f, 0.0f,
    };
    public static final float TEXTURE_ROTATED_180[] = {
            1.0f, 0.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f,
    };
    public static final float TEXTURE_ROTATED_270[] = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
    };

    private Rotation rotation=Rotation.NORMAL;
    
    public float[] getBufferCoordinates() {

        float[] flipCoordinates = TEXTURE_NO_ROTATION;

        switch (rotation){

            case ROTATION_90:

                flipCoordinates = TEXTURE_ROTATED_90;

                break;

            case ROTATION_180:

                flipCoordinates = TEXTURE_ROTATED_180;

                break;

            case ROTATION_270:

                flipCoordinates = TEXTURE_ROTATED_270;

                break;

        }


        if (scaleType == GPUImage.ScaleType.CENTER_CROP) {

            float distHorizontal = (1 - 1 / ratioWidthMax) / 2;
            float distVertical = (1 - 1 / ratioHeightMax) / 2;

            flipCoordinates = new float[]{
                    addDistance(flipCoordinates[0], distHorizontal), addDistance(flipCoordinates[1], distVertical),
                    addDistance(flipCoordinates[2], distHorizontal), addDistance(flipCoordinates[3], distVertical),
                    addDistance(flipCoordinates[4], distHorizontal), addDistance(flipCoordinates[5], distVertical),
                    addDistance(flipCoordinates[6], distHorizontal), addDistance(flipCoordinates[7], distVertical),
            };

        }

        return  flipCoordinates;

    }

    private float addDistance(float coordinate, float distance) {
        return coordinate == 0.0f ? distance : 1 - distance;
    }
    
    public void setRotation(Rotation rotation) {

        this.rotation = rotation;
    }

    private GPUImage.ScaleType scaleType=GPUImage.ScaleType.CENTER_INSIDE;

    public void setScaleType(GPUImage.ScaleType scaleType) {

        this.scaleType = scaleType;

    }

    public GPUImage.ScaleType getScaleType() {

        return scaleType;

    }

    private float ratio;

    public void setRatio(float ratio) {

        this.ratio = ratio;

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
