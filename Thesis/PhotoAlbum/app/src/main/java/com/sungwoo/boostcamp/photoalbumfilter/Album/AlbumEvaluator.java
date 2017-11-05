package com.sungwoo.boostcamp.photoalbumfilter.Album;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.sungwoo.boostcamp.photoalbumfilter.ObjectEvaluator;
import com.sungwoo.boostcamp.photoalbumfilter.SubjectEvaluator;

import java.io.IOException;

import static com.sungwoo.boostcamp.photoalbumfilter.SubjectEvaluator.INPUT_SIZE;

/**
 * Created by psw10 on 2017-10-20.
 */

class AlbumEvaluator {
    private static final String TAG = AlbumEvaluator.class.getSimpleName();
    private SubjectEvaluator mSubjectEvaluator;
    private ObjectEvaluator mObjectEvaluator;
    private Context mContext;
    private AlbumFilterEvaluatorListener mAlbumFilterEvaluatorListener;
    private boolean subjectBool;
    private boolean objectBool;
    private String mImageUrl;

    AlbumEvaluator(AssetManager assetManager, Context context, AlbumFilterEvaluatorListener albumFilterEvaluatorListener) {
        mSubjectEvaluator = new SubjectEvaluator(assetManager);
        mObjectEvaluator = new ObjectEvaluator(assetManager);
        mContext = context;
        mAlbumFilterEvaluatorListener = albumFilterEvaluatorListener;
    }

    void evaluateImageByModel(String imageUrl) {
        mImageUrl = imageUrl;
        new AlbumFilterEvaluatorTask().execute(imageUrl);
    }

    private class AlbumFilterEvaluatorTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... uriStr) {
            Bitmap imageBitmap = null;

            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse("file://" + uriStr[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, INPUT_SIZE, INPUT_SIZE, false);
            if (imageBitmap != null) {
                int subject = mSubjectEvaluator.evaluateImageByModel(imageBitmap);
                int object = mObjectEvaluator.evaluateImageByModel(imageBitmap);

                subjectBool = subject == 0;
                objectBool = object == 0;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            mAlbumFilterEvaluatorListener.evaluateImageFinished(mImageUrl, subjectBool, objectBool);
        }
    }

    interface AlbumFilterEvaluatorListener {
        void evaluateImageFinished(String mImageUrl, boolean subjectBool, boolean objectBool);
    }
}
