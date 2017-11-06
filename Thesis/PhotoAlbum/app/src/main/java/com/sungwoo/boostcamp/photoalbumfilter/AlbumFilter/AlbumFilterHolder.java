package com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoResultModel;
import com.sungwoo.boostcamp.photoalbumfilter.R;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter.AlbumFilterAdapter.NUM_IN_ONE_LINE;

/**
 * Created by psw10 on 2017-10-20.
 */

class AlbumFilterHolder extends RecyclerView.ViewHolder {

    @BindViews({R.id.recycler_view_item_image_view1, R.id.recycler_view_item_image_view2, R.id.recycler_view_item_image_view3})
    ImageView[] mRecyclerViewItemImageViews;
    @BindViews({R.id.recycler_view_item_filter_text_view1, R.id.recycler_view_item_filter_text_view2, R.id.recycler_view_item_filter_text_view3})
    TextView[] mRecyclerViewItemFilterTextViews;
    @BindViews({R.id.recycler_view_item_check_image_button1, R.id.recycler_view_item_check_image_button2, R.id.recycler_view_item_check_image_button3})
    ImageButton[] mRecyclerViewItemCheckImageButtons;
    private AlbumFilterAdapter.ItemClickListener mItemClickListener;
    private ImageInfoResultModel[] mImageInfoResultModels;


    private Context mContext;

    AlbumFilterHolder(ViewGroup itemView, Context context, AlbumFilterAdapter.ItemClickListener itemClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.pictures_recyclerview_item, itemView, false));
        ButterKnife.bind(this, this.itemView);
        mItemClickListener = itemClickListener;
        mContext = context;
    }

    void setItemContents(ImageInfoResultModel[] imageInfoResultModels) {
        mImageInfoResultModels = imageInfoResultModels;

        for (int i = 0; i < NUM_IN_ONE_LINE; i++) {
            if (imageInfoResultModels[i] != null) {
                Glide.with(mContext).load(imageInfoResultModels[i].getData()).override(120, 120).fitCenter().into(mRecyclerViewItemImageViews[i]);
                mRecyclerViewItemCheckImageButtons[i].setVisibility(View.VISIBLE);
                if (imageInfoResultModels[i].isChecked) {
                    mRecyclerViewItemCheckImageButtons[i].setImageResource(R.drawable.check_box_selector);
                } else {
                    mRecyclerViewItemCheckImageButtons[i].setImageResource(R.drawable.check_box_empty_selector);
                }
                mRecyclerViewItemFilterTextViews[i].setVisibility(View.VISIBLE);
                if (!imageInfoResultModels[i].getIsObjectOk() && !imageInfoResultModels[i].getIsSubjectOk()) {
                    mRecyclerViewItemFilterTextViews[i].setText("O S");
                    mRecyclerViewItemFilterTextViews[i].setBackground(mContext.getResources().getDrawable(R.drawable.o_s_item_background));
                } else if (!imageInfoResultModels[i].getIsObjectOk()) {
                    mRecyclerViewItemFilterTextViews[i].setText("O");
                    mRecyclerViewItemFilterTextViews[i].setBackground(mContext.getResources().getDrawable(R.drawable.object_item_background));
                } else if (!imageInfoResultModels[i].getIsSubjectOk()) {
                    mRecyclerViewItemFilterTextViews[i].setText("S");
                    mRecyclerViewItemFilterTextViews[i].setBackground(mContext.getResources().getDrawable(R.drawable.subject_item_background));
                } else {
                    mRecyclerViewItemFilterTextViews[i].setText("O K");
                    mRecyclerViewItemFilterTextViews[i].setBackground(mContext.getResources().getDrawable(R.drawable.non_item_background));
                }
            } else {
                mRecyclerViewItemImageViews[i].setImageResource(0);
                mRecyclerViewItemFilterTextViews[i].setVisibility(View.GONE);
                mRecyclerViewItemCheckImageButtons[i].setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.recycler_view_item_check_image_button1)
    void onRecyclerViewItemCheckImageButton1Click(ImageButton imageButton) {
        mItemClickListener.onItemClick(mImageInfoResultModels[0]);
//        changeImageViewSrc(imageButton, mImageInfoResultModels[0].isChecked);
    }

    @OnClick(R.id.recycler_view_item_check_image_button2)
    void onRecyclerViewItemCheckImageButton2Click(ImageButton imageButton) {
        mItemClickListener.onItemClick(mImageInfoResultModels[1]);
//        changeImageViewSrc(imageButton, mImageInfoResultModels[1].isChecked);
    }

    @OnClick(R.id.recycler_view_item_check_image_button3)
    void onRecyclerViewItemCheckImageButton3Click(ImageButton imageButton) {
        mItemClickListener.onItemClick(mImageInfoResultModels[2]);
//        changeImageViewSrc(imageButton, mImageInfoResultModels[2].isChecked);
    }

//    private void changeImageViewSrc(ImageButton imageButton, boolean isChecked) {
//        if (isChecked) {
//            imageButton.setImageResource(R.drawable.check_box_empty_selector);
//        } else {
//            imageButton.setImageResource(R.drawable.check_box_selector);
//        }
//    }
}
