package com.sungwoo.boostcamp.photoalbumfilter;

import android.graphics.Bitmap;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

/**
 * Created by psw10 on 2017-10-14.
 */

public class TensorflowInterface {
    private static final int INPUT_SIZE = 256;
    private TensorFlowInferenceInterface mTensorFlowInferenceInterface;

    TensorflowInterface() {
        mTensorFlowInferenceInterface = new TensorFlowInferenceInterface();
    }

    public int evaluateImageByModel(Bitmap bitmap) {
        float[] inputFloatArray;
        inputFloatArray = makeFloatArrayFromBitmap(bitmap);
        return 0;
    }

    private float[] makeFloatArrayFromBitmap(Bitmap bitmap) {
        float[] floatArray = new float[INPUT_SIZE * INPUT_SIZE * 3];
        int[] intArray = new int[INPUT_SIZE * INPUT_SIZE];
        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int i = 0; i < intArray.length; ++i) {
            final int val = intArray[i];
            floatArray[i * 3 + 0] = ((val >> 16) & 0xFF);
            floatArray[i * 3 + 1] = ((val >> 8) & 0xFF);
            floatArray[i * 3 + 2] = (val & 0xFF);
        }
        return floatArray;
    }
}
