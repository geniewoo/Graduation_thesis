package com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sungwoo.boostcamp.photoalbumfilter.Album.AlbumActivity;
import com.sungwoo.boostcamp.photoalbumfilter.CheckedRealmModel;
import com.sungwoo.boostcamp.photoalbumfilter.CommonUtils;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoManager;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoModel;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoResultModel;
import com.sungwoo.boostcamp.photoalbumfilter.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.view.View.GONE;
import static com.sungwoo.boostcamp.photoalbumfilter.Album.AlbumActivity.ALBUM_ACTIVITY_RESULT_CODE;
import static com.sungwoo.boostcamp.photoalbumfilter.Album.AlbumActivity.DELETED_IMAGE_INFO_MODELS;
import static com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter.AlbumFilterAdapter.FilterMode.O_S;
import static com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter.AlbumFilterAdapter.FilterMode.NON;
import static com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter.AlbumFilterAdapter.FilterMode.OBJECT_MODE;
import static com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter.AlbumFilterAdapter.FilterMode.SUBJECT_MODE;
import static com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter.AlbumFilterAdapter.NUM_IN_ONE_LINE;

public class AlbumFilterActivity extends AppCompatActivity implements ImageInfoManager.GalleryManagerListener, AlbumFilterEvaluator.AlbumFilterEvaluatorListener, AlbumFilterAdapter.ItemClickListener {
    private static final String TAG = AlbumFilterActivity.class.getSimpleName();

    @BindView(R.id.album_filter_recycler_view)
    RecyclerView mAlbumFilterRecyclerView;
    @BindView(R.id.album_filter_menu_linear_layout)
    LinearLayout albumFilterMenuLinearLayout;
    @BindView(R.id.album_filter_fixed_check_num_text_view)
    TextView mAlbumFilterFixedCheckNumTextView;
    @BindView(R.id.album_filter_top_nav_check_image_button)
    ImageButton mAlbumFilterTopNavCheckImageButton;
    @BindView(R.id.album_filter_fixed_delete_image_button)
    ImageButton mAlbumFilterFixedDeleteImageButton;
    @BindView(R.id.album_filter_fixed_filter_image_button)
    ImageButton mAlbumFilterFixedFilterImageButton;

    private AlbumFilterAdapter mAlbumFilterAdapter;
//    private static final String IMAGE_INFO_MODELS = "IMAGE_INFO_MODELS";
//    private static final String IMAGE_RESULT_ALL = "IMAGE_RESULT_ALL";
//    private static final String IMAGE_RESULT_OBJECT = "IMAGE_RESULT_OBJECT";
//    private static final String IMAGE_RESULT_SUBJECT = "IMAGE_RESULT_SUBJECT";
//    private static final String IMAGE_RESULT_NON = "IMAGE_RESULT_NON";
    private AlbumFilterEvaluator mAlbumFilterEvaluator;
    private static final String ALL_CHECK_BOX_CHECKED= "CHECKED";
    private static final String ALL_CHECK_BOX_UNCHECKED = "UNCHECKED";
    public static final int ALBUM_ACTIVITY_REQUEST_CODE = 123;
    private Realm mRealm;
    @BindViews({R.id.album_filter_top_bar_o_s_text_view, R.id.album_filter_top_bar_object_text_view, R.id.album_filter_top_bar_subject_text_view, R.id.album_filter_top_bar_non_text_view})
    TextView[] mAlbumFilterTopBarTextViewArray;

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_album_filter);
        ButterKnife.bind(this);
        if (isFirst) {
            mRealm = Realm.getDefaultInstance();
            mAlbumFilterEvaluator = new AlbumFilterEvaluator(getAssets(), getApplicationContext(), this);
            initAlbumFilterRecyclerView();

//            if (savedInstanceState != null) {
//                Log.i(TAG, "getSaveInstanceState");
//                mAlbumFilterAdapter.setO_sList(((ArrayList)savedInstanceState.getParcelableArrayList(IMAGE_RESULT_ALL)));
//                mAlbumFilterAdapter.setNonList(((ArrayList)savedInstanceState.getParcelableArrayList(IMAGE_RESULT_NON)));
//                mAlbumFilterAdapter.setSubjectList(((ArrayList)savedInstanceState.getParcelableArrayList(IMAGE_RESULT_SUBJECT)));
//                mAlbumFilterAdapter.setObjectList(((ArrayList)savedInstanceState.getParcelableArrayList(IMAGE_RESULT_OBJECT)));
//                mAlbumFilterEvaluator.setImageInfoModelList(((ArrayList)savedInstanceState.getParcelableArrayList(IMAGE_INFO_MODELS)));
//                mAlbumFilterAdapter.notifyDataSetChanged();
//                mAlbumFilterEvaluator.evaluateImageByModel();
//            } else {
                ImageInfoManager imageInfoManager = new ImageInfoManager(this, this);
                imageInfoManager.getImageInfo();
//            }
            isFirst = false;
        }
    }

    private void initAlbumFilterRecyclerView() {
        mAlbumFilterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAlbumFilterAdapter = new AlbumFilterAdapter(this, this);
        mAlbumFilterRecyclerView.setAdapter(mAlbumFilterAdapter);
        mAlbumFilterRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    mAlbumFilterFixedCheckNumTextView.setVisibility(View.INVISIBLE);
                    mAlbumFilterFixedDeleteImageButton.setVisibility(View.INVISIBLE);
                    mAlbumFilterFixedFilterImageButton.setVisibility(View.INVISIBLE);
                } else {
                    mAlbumFilterFixedCheckNumTextView.setVisibility(View.VISIBLE);
                    mAlbumFilterFixedDeleteImageButton.setVisibility(View.VISIBLE);
                    mAlbumFilterFixedFilterImageButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void GetImageModelFinished(List<ImageInfoModel> infoModelList) {
        infoModelList = removeCheckedModels(infoModelList, getCheckedListFromRealm());
        mAlbumFilterEvaluator.setImageInfoModelList(infoModelList);
        mAlbumFilterEvaluator.evaluateImageByModel();
    }

    private List<CheckedRealmModel> getCheckedListFromRealm() {
        List<CheckedRealmModel> checkedRealmModelList = mRealm.copyFromRealm(mRealm.where(CheckedRealmModel.class).findAll());
        return checkedRealmModelList;
    }

    private List<ImageInfoModel> removeCheckedModels(List<ImageInfoModel> imageInfoModelList, List<CheckedRealmModel> checkedRealmModels) {
        for (int i = checkedRealmModels.size() - 1; i >= 0; i --){
            boolean isExist = false;
            for (int j = 0; j < imageInfoModelList.size(); j ++) {
                if (checkedRealmModels.get(i).imageUrl.equals(imageInfoModelList.get(j).getData())) {
                    imageInfoModelList.remove(j);
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                checkedRealmModels.remove(i);
            }
        }

        mRealm.beginTransaction();
        mRealm.where(CheckedRealmModel.class).findAll().deleteAllFromRealm();
        mRealm.insert(checkedRealmModels);
        mRealm.commitTransaction();

        return imageInfoModelList;
    }

    @Override
    public void evaluateImageFinished(ImageInfoResultModel infoModelList) {
        int size = mAlbumFilterAdapter.updateImageInfoResultLists(infoModelList);
        if (size != 0) {
            if (size % NUM_IN_ONE_LINE == 1) {
                mAlbumFilterAdapter.notifyItemInserted(mAlbumFilterAdapter.getItemCount());
            } else {
                mAlbumFilterAdapter.notifyItemChanged(mAlbumFilterAdapter.getItemCount() - 1);
            }
        }
    }

    @OnClick(R.id.album_filter_o_s_button)
    void onAlbumFilterO_SButtonClick() {
        mAlbumFilterAdapter.changeFilter(O_S);
        doSomethingAlbumFilterChanged();
        mAlbumFilterTopBarTextViewArray[0].setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.album_filter_object_button)
    void onAlbumFilterObjectButtonClick() {
        mAlbumFilterAdapter.changeFilter(OBJECT_MODE);
        doSomethingAlbumFilterChanged();
        mAlbumFilterTopBarTextViewArray[1].setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.album_filter_subject_button)
    void onAlbumFilterSubjectButtonClick() {
        mAlbumFilterAdapter.changeFilter(SUBJECT_MODE);
        doSomethingAlbumFilterChanged();
        mAlbumFilterTopBarTextViewArray[2].setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.album_filter_non_button)
    void onAlbumFilterNonButtonClick() {
        mAlbumFilterAdapter.changeFilter(NON);
        doSomethingAlbumFilterChanged();
        mAlbumFilterTopBarTextViewArray[3].setVisibility(View.VISIBLE);
    }

    private void doSomethingAlbumFilterChanged() {
        mAlbumFilterAdapter.clearDeleteInfoResultModelList();
        changeCheckNum();
        mAlbumFilterAdapter.notifyDataSetChanged();
        onAlbumFilterFixedImageButtonClick();
        setVisibilityGoneTextViews();
        mAlbumFilterTopNavCheckImageButton.setImageResource(R.drawable.check_box_reverse_selector);
        mAlbumFilterTopNavCheckImageButton.setTag(ALL_CHECK_BOX_UNCHECKED);
    }

    private void setVisibilityGoneTextViews() {
        for (TextView textView : mAlbumFilterTopBarTextViewArray) {
            textView.setVisibility(GONE);
        }
    }

    @OnClick(R.id.album_filter_fixed_filter_image_button)
    void onAlbumFilterFixedImageButtonClick() {
        if (albumFilterMenuLinearLayout.getVisibility() == GONE)
            albumFilterMenuLinearLayout.setVisibility(View.VISIBLE);
        else
            albumFilterMenuLinearLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.album_filter_top_nav_album_image_button)
    void onAlbumFilterTopNavAlbumImageButtonClick() {
        startActivityForResult(new Intent(this, AlbumActivity.class), ALBUM_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ALBUM_ACTIVITY_REQUEST_CODE && resultCode == ALBUM_ACTIVITY_RESULT_CODE) {
            List<ImageInfoModel> imageInfoModelList = (List<ImageInfoModel>)data.getSerializableExtra(DELETED_IMAGE_INFO_MODELS);
            mAlbumFilterAdapter.setDeleteImageInfoModelList(imageInfoModelList);
            if (!mAlbumFilterEvaluator.isEvaluatorIsWorking()) {
                mAlbumFilterAdapter.removeModelsFromImageInfoModelList();
            }
        }
    }

    @Override
    public void onItemClick(ImageInfoResultModel imageInfoResultModel) {
        mAlbumFilterAdapter.changeImageDeleteInfoResultModelList(imageInfoResultModel);
        changeCheckNum();
        mAlbumFilterAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.album_filter_fixed_delete_image_button)
    void onAlbumFilterFixedDeleteImageButtonClick() {
        if (mAlbumFilterAdapter.getDeleteImageInfoResultModelListSize() == 0) {
            CommonUtils.showCheckRemainingDialog(AlbumFilterActivity.this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    List<CheckedRealmModel> checkedRealmModelList = mRealm.copyFromRealm(mRealm.where(CheckedRealmModel.class).findAll());
                    for (ImageInfoResultModel imageInfoResultModel : mAlbumFilterAdapter.getNonList()) {
                        boolean isExist = false;
                        for (int i = 0; i < checkedRealmModelList.size(); i ++) {
                            if (checkedRealmModelList.get(i).imageUrl.equals(imageInfoResultModel.getData())) {
                                isExist = true;
                                break;
                            }
                        }
                        if (!isExist)
                            checkedRealmModelList.add(new CheckedRealmModel(imageInfoResultModel.getData()));
                    }
                    mRealm.beginTransaction();
                    mRealm.where(CheckedRealmModel.class).findAll().deleteAllFromRealm();
                    mRealm.insert(checkedRealmModelList);
                    mRealm.commitTransaction();
                }
            });
        } else {
            CommonUtils.showDeleteDialog(this, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mAlbumFilterAdapter.removeModelsFromImageInfoResultModelList();
                    changeCheckNum();
                    CommonUtils.showCheckRemainingDialog(AlbumFilterActivity.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            List<CheckedRealmModel> checkedRealmModelList = mRealm.copyFromRealm(mRealm.where(CheckedRealmModel.class).findAll());
                            for (ImageInfoResultModel imageInfoResultModel : mAlbumFilterAdapter.getNonList()) {
                                boolean isExist = false;
                                for (int i = 0; i < checkedRealmModelList.size(); i++) {
                                    if (checkedRealmModelList.get(i).imageUrl.equals(imageInfoResultModel.getData())) {
                                        isExist = true;
                                        break;
                                    }
                                }
                                if (!isExist)
                                    checkedRealmModelList.add(new CheckedRealmModel(imageInfoResultModel.getData()));
                            }
                            mRealm.beginTransaction();
                            mRealm.where(CheckedRealmModel.class).findAll().deleteAllFromRealm();
                            mRealm.insert(checkedRealmModelList);
                            mRealm.commitTransaction();
                        }
                    });
                }
            });
        }
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(IMAGE_INFO_MODELS, new ArrayList<>(mAlbumFilterEvaluator.getImageInfoModelListFromQuit()));
//        outState.putParcelableArrayList(IMAGE_RESULT_ALL, new ArrayList<>(mAlbumFilterAdapter.getO_sList()));
//        outState.putParcelableArrayList(IMAGE_RESULT_NON, new ArrayList<>(mAlbumFilterAdapter.getNonList()));
//        outState.putParcelableArrayList(IMAGE_RESULT_OBJECT, new ArrayList<>(mAlbumFilterAdapter.getObjectList()));
//        outState.putParcelableArrayList(IMAGE_RESULT_SUBJECT, new ArrayList<>(mAlbumFilterAdapter.getSubjectList()));
//        Log.i(TAG, "onSaveInstanceState");
//    }

    private void changeCheckNum() {
        mAlbumFilterFixedCheckNumTextView.setText("checked : " + mAlbumFilterAdapter.getDeleteImageInfoResultModelListSize());
        if (mAlbumFilterAdapter.getDeleteImageInfoResultModelListSize() == 0) {
            mAlbumFilterFixedDeleteImageButton.setImageResource(R.drawable.confirm_selector);
        } else {
            mAlbumFilterFixedDeleteImageButton.setImageResource(R.drawable.garbage_selector);
        }
    }

    @OnClick(R.id.album_filter_top_nav_check_image_button)
    void onAlbumFilterTopNavCheckImageButton(ImageButton imageButton) {
        if (ALL_CHECK_BOX_UNCHECKED == imageButton.getTag() || imageButton.getTag() == null) {
            imageButton.setImageResource(R.drawable.check_box_selector);
            imageButton.setTag(ALL_CHECK_BOX_CHECKED);
            mAlbumFilterAdapter.addAllListToDeleteModelList();
            mAlbumFilterAdapter.changeDeleteModelsCheckState(true);
            mAlbumFilterAdapter.notifyDataSetChanged();
            changeCheckNum();
        } else {
            imageButton.setImageResource(R.drawable.check_box_reverse_selector);
            imageButton.setTag(ALL_CHECK_BOX_UNCHECKED);
            mAlbumFilterAdapter.removeAllListFromDeleteModelList();
            mAlbumFilterAdapter.changeDeleteModelsCheckState(false);
            mAlbumFilterAdapter.notifyDataSetChanged();
            changeCheckNum();
        }
    }
}
