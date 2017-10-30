package com.sungwoo.boostcamp.photoalbumfilter;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

/**
 * Created by psw10 on 2017-10-14.
 */

public class ObjectEvaluator {
    public static final int INPUT_SIZE = 256;
    private static final int NUM_CLASS = 2;
    private static final String MODEL_FILE = "file:///android_asset/small_045_001_new.pb";
    private static final String PLACEHOLDER_NAME1 = "input";
    private static final String OUTPUT_NAME = "output";
    private static final String[] OUTPUT_NAMES = new String[]{OUTPUT_NAME};
    private TensorFlowInferenceInterface mTensorFlowInferenceInterface;

    public ObjectEvaluator(AssetManager assetManager) {
        mTensorFlowInferenceInterface = new TensorFlowInferenceInterface();
        if (mTensorFlowInferenceInterface.initializeTensorFlow(assetManager, MODEL_FILE) != 0) {
            throw new RuntimeException("TF initialization failed");
        }
    }

    public int evaluateImageByModel(Bitmap bitmap) {
        float[] inputFloatArray;
        inputFloatArray = makeFloatArrayFromBitmap(bitmap);
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
