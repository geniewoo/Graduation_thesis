package com.sungwoo.boostcamp.photoalbumfilter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by psw10 on 2017-10-17.
 */

public class ImageInfoManager {
    private static final String TAG = ImageInfoManager.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;
    private GalleryManagerListener mGalleryManagerListener;
    private ArrayList<ImageInfoModel> mImageInfoModelList = new ArrayList<>();

    public ImageInfoManager(Context context, GalleryManagerListener galleryManagerListener) {
        mContext = context;
        mGalleryManagerListener = galleryManagerListener;
    }

    private void getCursor() {
        final String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String sortOrder = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
        if (CommonUtils.CheckRead_External_Permission(mContext)) {
            mCursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
            int dataColumnIndex = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            for (int i = 0; i < mCursor.getCount(); i++) {
                mCursor.moveToPosition(i);

                String dataStr = mCursor.getString(dataColumnIndex);
                mImageInfoModelList.add(new ImageInfoModel(dataStr));
            }
        }
    }

    public void getImageInfo() {
        new GetGalleryCursorTask().execute();
    }

    private class GetGalleryCursorTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getCursor();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mGalleryManagerListener.GetImageModelFinished(mImageInfoModelList);
        }
    }

    public interface GalleryManagerListener {
        void GetImageModelFinished(List<ImageInfoModel> infoModelList);
    }
}
