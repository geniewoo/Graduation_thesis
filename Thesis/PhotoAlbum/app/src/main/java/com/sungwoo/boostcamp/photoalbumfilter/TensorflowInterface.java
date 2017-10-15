package com.sungwoo.boostcamp.photoalbumfilter;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

/**
 * Created by psw10 on 2017-10-14.
 */

public class TensorflowInterface {
    public static final int INPUT_SIZE = 256;
    private static final int NUM_CLASS = 2;
    private static final float[] KEEP_PROP = {1f};
    private static final float[] CLASS = {1, 0};
    private static final String MODEL_FILE = "file:///android_asset/small_045_001.pb";
    private static final String PLACEHOLDER_NAME1 = "Placeholder";
    private static final String PLACEHOLDER_NAME2 = "Placeholder_1";
    private static final String PLACEHOLDER_NAME3 = "Placeholder_2";
    private static final String OUTPUT_NAME = "MatMul_2";
    private static final String[] OUTPUT_NAMES = new String[]{OUTPUT_NAME};
    private TensorFlowInferenceInterface mTensorFlowInferenceInterface;

    TensorflowInterface(AssetManager assetManager) {
        mTensorFlowInferenceInterface = new TensorFlowInferenceInterface();
        mTensorFlowInferenceInterface.initializeTensorFlow(assetManager, MODEL_FILE);
        Log.i("쉐입", String.valueOf(mTensorFlowInferenceInterface.graph().operation(OUTPUT_NAME).output(0).shape()));
    }

    int evaluateImageByModel(Bitmap bitmap) {
        float[] inputFloatArray;
        inputFloatArray = makeFloatArrayFromBitmap(bitmap);
//        for (int i = 0; i < inputFloatArray.length; i++)
//            Log.i("스트링" + i, String.valueOf(inputFloatArray[i]));
        float[] outputFloatArray = executeLearningModel(inputFloatArray);
        return analysisOutput(outputFloatArray);
    }

    private float[] makeFloatArrayFromBitmap(Bitmap bitmap) {
        float[] floatArray = new float[INPUT_SIZE * INPUT_SIZE * 3];
        int[] intArray = new int[INPUT_SIZE * INPUT_SIZE];
        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int i = 0; i < intArray.length; ++i) {
            final int val = intArray[i];
            floatArray[i * 3] = ((val >> 16) & 0xFF);
            floatArray[i * 3 + 1] = ((val >> 8) & 0xFF);
            floatArray[i * 3 + 2] = (val & 0xFF);
        }
        return floatArray;
    }

    private float[] executeLearningModel(float[] inputFloatArray) {
        float[] outputFloatArray = new float[NUM_CLASS];
        mTensorFlowInferenceInterface.fillNodeFloat(PLACEHOLDER_NAME1, new int[]{1, INPUT_SIZE, INPUT_SIZE, 3}, inputFloatArray);
        mTensorFlowInferenceInterface.fillNodeFloat(PLACEHOLDER_NAME2, new int[]{1}, KEEP_PROP);
        //mTensorFlowInferenceInterface.fillNodeFloat(PLACEHOLDER_NAME3, new int[]{2}, CLASS);
        mTensorFlowInferenceInterface.runInference(OUTPUT_NAMES);
        mTensorFlowInferenceInterface.readNodeFloat(OUTPUT_NAME, outputFloatArray);
        return outputFloatArray;
    }

    private int analysisOutput(float[] outputFloatArray) {
        if (outputFloatArray[0] > outputFloatArray[1])
            return 0;
        else
            return 1;
    }
}
