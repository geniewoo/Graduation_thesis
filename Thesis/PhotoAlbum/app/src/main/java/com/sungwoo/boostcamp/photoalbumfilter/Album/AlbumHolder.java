package com.sungwoo.boostcamp.photoalbumfilter.Album;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoModel;
import com.sungwoo.boostcamp.photoalbumfilter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sungwoo.boostcamp.photoalbumfilter.Album.AlbumAdapter.NUM_IN_ONE_LINE;

/**
 * Created by psw10 on 2017-10-18.
 */

class AlbumHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recycler_view_item_image_view1)
    ImageView mRecyclerViewItemImageView1;
    @BindView(R.id.recycler_view_item_image_view2)
    ImageView mRecyclerViewItemImageView2;
    @BindView(R.id.recycler_view_item_image_view3)
    ImageView mRecyclerViewItemImageView3;
    private Context mContext;
    private ImageView[] mImageViews;
    private AlbumAdapter.ItemClickListener mItemClickListener;
    private ImageInfoModel[] mImageInfoModelArray;

    AlbumHolder(ViewGroup itemView, Context context, AlbumAdapter.ItemClickListener itemClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.pictures_recyclerview_item, itemView, false));
        ButterKnife.bind(this, this.itemView);
        mImageViews = new ImageView[]{mRecyclerViewItemImageView1, mRecyclerViewItemImageView2, mRecyclerViewItemImageView3};
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    void setItemContents(ImageInfoModel[] imageInfoModelArray) {
        mImageInfoModelArray = imageInfoModelArray;
        for (int i = 0; i < NUM_IN_ONE_LINE; i++) {
            if (imageInfoModelArray[i] != null) {
                Glide.with(mContext).load(imageInfoModelArray[i].getData()).override(120, 120).fitCenter().into(mImageViews[i]);
            }
        }
    }

    @OnClick(R.id.recycler_view_item_image_view1)
    void onRecyclerViewItemImageVIew1Click() {
        mItemClickListener.onItemClick(mImageInfoModelArray[0]);
    }

    @OnClick(R.id.recycler_view_item_image_view2)
    void onRecyclerViewItemImageVIew2Click() {
        mItemClickListener.onItemClick(mImageInfoModelArray[1]);
    }

    @OnClick(R.id.recycler_view_item_image_view3)
    void onRecyclerViewItemImageVIew3Click() {
        mItemClickListener.onItemClick(mImageInfoModelArray[2]);
    }
}
