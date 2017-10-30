package com.sungwoo.boostcamp.photoalbumfilter.Album;

import android.content.ContentResolver;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sungwoo.boostcamp.photoalbumfilter.CommonUtils;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoManager;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoModel;
import com.sungwoo.boostcamp.photoalbumfilter.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlbumActivity extends AppCompatActivity implements ImageInfoManager.GalleryManagerListener, AlbumAdapter.ItemClickListener, AlbumEvaluator.AlbumFilterEvaluatorListener {
    private static final String TAG = AlbumActivity.class.getSimpleName();
    private AlbumAdapter mAlbumAdapter;
    private AlbumEvaluator mAlbumEvaluator;
    private ImageInfoModel mImageInfoModel;
    private ArrayList<ImageInfoModel> mDeletedImageInfoModelArrayList = new ArrayList<>();
    public static final int ALBUM_ACTIVITY_RESULT_CODE = 789;
    public static final String DELETED_IMAGE_INFO_MODELS = "DELETED_IMAGE_INFO_MODELS";

    @BindView(R.id.album_recycler_view)
    RecyclerView mAlbumRecyclerView;
    @BindView(R.id.album_detail_linear_layout)
    LinearLayout mAlbumDetailLinearLayout;
    @BindView(R.id.album_detail_image_view)
    ImageView mAlbumDetailImageView;
    @BindView(R.id.album_detail_object_image_view)
    ImageView mAlbumDetailObjectImageView;
    @BindView(R.id.album_detail_subject_image_view)
    ImageView mAlbumDetailSubjectImageView;
    @BindView(R.id.album_progress_bar)
    ProgressBar mAlbumProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
//        mAlbumDetailLinearLayout.bringToFront();
        initAlbumRecyclerView();
        mAlbumEvaluator = new AlbumEvaluator(getAssets(), this, this);
    }

    private void evaluatePicture(String dataUrl) {
        mAlbumEvaluator.evaluateImageByModel(dataUrl);
    }

    private void initAlbumRecyclerView() {
        mAlbumRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAlbumAdapter = new AlbumAdapter(this, this);
        mAlbumRecyclerView.setAdapter(mAlbumAdapter);
        ImageInfoManager imageInfoManager = new ImageInfoManager(this, this);
        imageInfoManager.getImageInfo();
    }

    private void showResultPage(String dataUrl, boolean subjectBool, boolean objectBool) {
        mAlbumProgressBar.setVisibility(View.GONE);
        mAlbumDetailLinearLayout.setVisibility(View.VISIBLE);
        Glide.with(this).load(dataUrl).into(mAlbumDetailImageView);
        if (subjectBool) {
            mAlbumDetailSubjectImageView.setImageResource(R.drawable.check_mark);
        } else {
            mAlbumDetailSubjectImageView.setImageResource(R.drawable.cancel);
        }
        if (objectBool) {
            mAlbumDetailObjectImageView.setImageResource(R.drawable.check_mark);
        } else {
            mAlbumDetailObjectImageView.setImageResource(R.drawable.cancel);
        }
    }

    @Override
    public void GetImageModelFinished(List<ImageInfoModel> imageInfoModelArrayList) {
        mAlbumAdapter.setImageInfoModelList(imageInfoModelArrayList);
        mAlbumAdapter.notifyItemInserted(mAlbumAdapter.getItemCount());
//        if (index % NUM_IN_ONE_LINE == 0) {
//            mAlbumFilterAdapter.notifyItemInserted(mAlbumFilterAdapter.getItemCount());
//        } else {
//            mAlbumFilterAdapter.notifyItemChanged(mAlbumFilterAdapter.getItemCount() - 1);
//        }
    }

    @Override
    public void onItemClick(ImageInfoModel imageInfoModel) {
        mAlbumProgressBar.setVisibility(View.VISIBLE);
        mImageInfoModel = imageInfoModel;
        evaluatePicture(imageInfoModel.getData());
    }

    @OnClick(R.id.album_detail_linear_layout)
    void onAlbumDetailLinearLayoutClick() {
     mAlbumDetailLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void evaluateImageFinished(String imageUrl, boolean subjectBool, boolean objectBool) {
        showResultPage(imageUrl, subjectBool, objectBool);
    }

    @OnClick(R.id.album_detail_delete_image_button)
    void onAlbumDetailDeleteImageButton() {
        mAlbumAdapter.deleteModel(mImageInfoModel);

        if (CommonUtils.CheckWrite_External_Permission(this)) {
            ContentResolver contentResolver = getContentResolver();
            contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.Images.ImageColumns.DATA + "=?", new String[]{mImageInfoModel.getData()});
            mDeletedImageInfoModelArrayList.add(mImageInfoModel);
            onAlbumDetailLinearLayoutClick();
            Toast.makeText(this, "사진이 삭제 되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.album_back_image_button)
    void onAlbumBackImageButtonClick() {
        if (mDeletedImageInfoModelArrayList.size() != 0) {
            Intent intent = new Intent();
            intent.putExtra(DELETED_IMAGE_INFO_MODELS, mDeletedImageInfoModelArrayList);
            setResult(ALBUM_ACTIVITY_RESULT_CODE, intent);
            finish();
        }
        finish();
    }

//    @Override
//    public void onBackPressed() {
//
//    }
}
