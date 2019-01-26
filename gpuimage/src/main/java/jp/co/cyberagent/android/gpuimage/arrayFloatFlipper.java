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

        float[] rotatedTex= flipCoordinates;

        if (flipHorizontal) {
            rotatedTex = new float[]{
                    flip(rotatedTex[0]), rotatedTex[1],
                    flip(rotatedTex[2]), rotatedTex[3],
                    flip(rotatedTex[4]), rotatedTex[5],
                    flip(rotatedTex[6]), rotatedTex[7],
            };
        }
        if (flipVertical) {
            rotatedTex = new float[]{
                    rotatedTex[0], flip(rotatedTex[1]),
                    rotatedTex[2], flip(rotatedTex[3]),
                    rotatedTex[4], flip(rotatedTex[5]),
                    rotatedTex[6], flip(rotatedTex[7]),
            };
        }

        if (scaleType == GPUImage.ScaleType.CENTER_CROP) {

            float distHorizontal = (1 - 1 / ratioWidthMax) / 2;
            float distVertical = (1 - 1 / ratioHeightMax) / 2;

            flipCoordinates = new float[]{
                    addDistance(rotatedTex[0], distHorizontal), addDistance(rotatedTex[1], distVertical),
                    addDistance(rotatedTex[2], distHorizontal), addDistance(rotatedTex[3], distVertical),
                    addDistance(rotatedTex[4], distHorizontal), addDistance(rotatedTex[5], distVertical),
                    addDistance(rotatedTex[6], distHorizontal), addDistance(rotatedTex[7], distVertical),
            };

        }

        return  flipCoordinates;

    }

    private static float flip(final float i) {
        if (i == 0.0f) {
            return 1.0f;
        }
        return 0.0f;
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

    private boolean flipVertical;
    private boolean flipHorizontal;

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
}
