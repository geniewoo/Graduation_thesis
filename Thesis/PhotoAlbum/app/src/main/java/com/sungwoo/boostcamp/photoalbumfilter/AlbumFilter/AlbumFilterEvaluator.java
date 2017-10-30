package com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoModel;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoResultModel;
import com.sungwoo.boostcamp.photoalbumfilter.SubjectEvaluator;

import java.io.IOException;
import java.util.List;

import static com.sungwoo.boostcamp.photoalbumfilter.SubjectEvaluator.INPUT_SIZE;

/**
 * Created by psw10 on 2017-10-20.
 */

class AlbumFilterEvaluator {
    private static final String TAG = AlbumFilterEvaluator.class.getSimpleName();
    private SubjectEvaluator mSubjectEvaluator;
    private List<ImageInfoModel> mImageInfoModelList;
    private Context mContext;
    private int index = 0;
    private boolean evaluatorIsWorking = false;
    private AlbumFilterEvaluatorListener mAlbumFilterEvaluatorListener;

    AlbumFilterEvaluator(AssetManager assetManager, Context context, AlbumFilterEvaluatorListener albumFilterEvaluatorListener) {
        mSubjectEvaluator = new SubjectEvaluator(assetManager);
        mContext = context;
        mAlbumFilterEvaluatorListener = albumFilterEvaluatorListener;
    }

    List<ImageInfoModel> getImageInfoModelListFromQuit() {
        return mImageInfoModelList.subList(index, mImageInfoModelList.size());
    }

    void setImageInfoModelList(List<ImageInfoModel> mImageInfoModelList) {
        this.mImageInfoModelList = mImageInfoModelList;
    }

    void setIndexZero() {
        index = 0;
    }

    void evaluateImageByModel() {
        if (index < mImageInfoModelList.size()) {
            evaluatorIsWorking = true;
            String imageUrl = mImageInfoModelList.get(index).getData();
            new AlbumFilterEvaluatorTask().execute(imageUrl);
        } else
            evaluatorIsWorking = false;
    }

    boolean isEvaluatorIsWorking() {
        return evaluatorIsWorking;
    }

    class AlbumFilterEvaluatorTask extends AsyncTask<String, Void, ImageInfoResultModel> {
        @Override
        protected ImageInfoResultModel doInBackground(String... uriStr) {
            Bitmap imageBitmap = null;

            try {
                Log.i(TAG, uriStr[0]);
                imageBitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse("file://" + uriStr[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, INPUT_SIZE, INPUT_SIZE, false);
            if (imageBitmap != null) {
                int subject = mSubjectEvaluator.evaluateImageByModel(imageBitmap);
                int object = mSubjectEvaluator.evaluateImageByModel(imageBitmap);

                boolean subjectBool = subject == 0;
                boolean objectBool = object == 0;

                return new ImageInfoResultModel(uriStr[0], subjectBool, objectBool);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ImageInfoResultModel results) {
            if (results != null) {
                mAlbumFilterEvaluatorListener.evaluateImageFinished(results);
                index ++;
                evaluateImageByModel();
            }
        }
    }

    interface AlbumFilterEvaluatorListener {
        void evaluateImageFinished(ImageInfoResultModel infoModelList);
    }
}
