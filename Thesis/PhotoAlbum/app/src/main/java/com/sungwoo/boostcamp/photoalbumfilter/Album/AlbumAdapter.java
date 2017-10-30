package com.sungwoo.boostcamp.photoalbumfilter.Album;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoModel;

import java.util.List;

/**
 * Created by psw10 on 2017-10-18.
 */

class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {
    private static final String TAG = AlbumAdapter.class.getSimpleName();
    static final int NUM_IN_ONE_LINE = 3;
//    private Cursor mCursor;
    private Context mContext;
    private List<ImageInfoModel> mImageInfoModelList;
    private ItemClickListener mItemClickListener;

    AlbumAdapter(Context context, ItemClickListener itemClickListener) {
        this.mContext = context;
        mItemClickListener = itemClickListener;
    }

    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumHolder(parent, mContext, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(AlbumHolder holder, int position) {
//        Bitmap[] thubnailArray = new Bitmap[NUM_IN_ONE_LINE];
//        for (int i = 0; i < NUM_IN_ONE_LINE; i ++) {
//            int picturePosition = position * NUM_IN_ONE_LINE + i;
//            if (!mCursor.moveToPosition(picturePosition)) break;
//            int id = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Media._ID));
////            int dataColumnIndex = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
//            thubnailArray[i] = MediaStore.Images.Thumbnails.getThumbnail(mContext.getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND, null);
//        }
//        holder.setItemContents(thubnailArray);

        ImageInfoModel[] imageInfoModelArray = new ImageInfoModel[NUM_IN_ONE_LINE];
        for (int i = 0; i < NUM_IN_ONE_LINE; i ++) {
            if (mImageInfoModelList.size() <= position * NUM_IN_ONE_LINE + i) break;

            imageInfoModelArray[i] = mImageInfoModelList.get(position * 3 + i);
        }
        holder.setItemContents(imageInfoModelArray);
    }

    @Override
    public int getItemCount() {
//        if (mCursor != null) {
//            return (mCursor.getCount() + NUM_IN_ONE_LINE - 1) / NUM_IN_ONE_LINE;
//        } else {
//            return 0;
//        }
        if (mImageInfoModelList!= null)
            return (mImageInfoModelList.size() + NUM_IN_ONE_LINE - 1) / NUM_IN_ONE_LINE;
        else return 0;
    }

    void setImageInfoModelList(List<ImageInfoModel> imageInfoModelArrayList) {
        mImageInfoModelList = imageInfoModelArrayList;
    }

    interface ItemClickListener {
        void onItemClick(ImageInfoModel imageInfoModel);
    }

    void deleteModel(ImageInfoModel imageInfoModel) {
        mImageInfoModelList.remove(imageInfoModel);
        notifyDataSetChanged();
    }
}
