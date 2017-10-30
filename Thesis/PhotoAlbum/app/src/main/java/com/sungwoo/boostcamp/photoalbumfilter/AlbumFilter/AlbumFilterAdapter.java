package com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sungwoo.boostcamp.photoalbumfilter.CommonUtils;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoModel;
import com.sungwoo.boostcamp.photoalbumfilter.ImageInfoResultModel;

import java.util.ArrayList;
import java.util.List;

import static com.sungwoo.boostcamp.photoalbumfilter.AlbumFilter.AlbumFilterAdapter.FilterMode.NON;

/**
 * Created by psw10 on 2017-10-20.
 */

class AlbumFilterAdapter extends RecyclerView.Adapter<AlbumFilterHolder> {

    private static final String TAG = AlbumFilterAdapter.class.getSimpleName();
    private ItemClickListener mItemClickListener;
    private Context mContext;
    private List<ImageInfoResultModel> nonList = new ArrayList<>();
    private List<ImageInfoResultModel> objectList = new ArrayList<>();
    private List<ImageInfoResultModel> subjectList = new ArrayList<>();
    private List<ImageInfoResultModel> o_sList = new ArrayList<>();
    private List<ImageInfoResultModel> nowList = nonList;
    private List<ImageInfoModel> mDeleteImageInfoModelList;
    private List<ImageInfoResultModel> mDeleteImageInfoResultModelList = new ArrayList<>();
    static final int NUM_IN_ONE_LINE = 3;
    private FilterMode mFilterMode = NON;

    enum FilterMode {
        NON,
        OBJECT_MODE,
        SUBJECT_MODE,
        O_S
    }

    AlbumFilterAdapter(Context context, ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        this.mContext = context;
    }

    List<ImageInfoResultModel> getNonList() {
        return nonList;
    }

    List<ImageInfoResultModel> getObjectList() {
        return objectList;
    }

    List<ImageInfoResultModel> getSubjectList() {
        return subjectList;
    }

    List<ImageInfoResultModel> getO_sList() {
        return o_sList;
    }

    void setNonList(List<ImageInfoResultModel> nonList) {
        this.nonList = nonList;
    }

    void setObjectList(List<ImageInfoResultModel> objectList) {
        this.objectList = objectList;
    }

    void setSubjectList(List<ImageInfoResultModel> subjectList) {
        this.subjectList = subjectList;
    }

    void setO_sList(List<ImageInfoResultModel> o_sList) {
        this.o_sList = o_sList;
    }

    @Override
    public AlbumFilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumFilterHolder(parent, mContext, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(AlbumFilterHolder holder, int position) {
        ImageInfoResultModel[] imageInfoResultModels = new ImageInfoResultModel[NUM_IN_ONE_LINE];

        for (int i = 0; i < NUM_IN_ONE_LINE; i ++) {
            if (position * NUM_IN_ONE_LINE + i < nowList.size()) {
                imageInfoResultModels[i] = nowList.get(position * NUM_IN_ONE_LINE + i);
            }
        }
        holder.setItemContents(imageInfoResultModels);
    }

    @Override
    public int getItemCount() {
        int itemCount = (nowList.size() == 0)? 0 : (nowList.size() + NUM_IN_ONE_LINE - 1) / NUM_IN_ONE_LINE;
        return itemCount;
    }

    int updateImageInfoResultLists(ImageInfoResultModel imageInfoResultModel) {
        int nowSize = nowList.size();
        nonList.add(imageInfoResultModel);
        if (!imageInfoResultModel.getIsObjectOk() || !imageInfoResultModel.getIsSubjectOk()) {
            o_sList.add(imageInfoResultModel);
        }
        if (!imageInfoResultModel.getIsSubjectOk()) {
            subjectList.add(imageInfoResultModel);
        }
        if (!imageInfoResultModel.getIsObjectOk()) {
            objectList.add(imageInfoResultModel);
        }

        if (mDeleteImageInfoModelList != null) {
            removeModelsFromImageInfoModelList();
        }

        if (nowSize != nowList.size()) {
            return nowList.size();
        }
        return 0;
    }

    void changeFilter(FilterMode filterMode) {
        changeDeleteModelsCheckState(false);
        mFilterMode = filterMode;
        switch (filterMode) {
            case O_S:
                nowList = o_sList;
                break;
            case NON:
                nowList = nonList;
                break;
            case OBJECT_MODE:
                nowList = objectList;
                break;
            case SUBJECT_MODE:
                nowList = subjectList;
                break;
        }
    }

    void setDeleteImageInfoModelList(List<ImageInfoModel> deleteImageInfoModelList) {
        this.mDeleteImageInfoModelList = deleteImageInfoModelList;
    }

    void removeModelsFromImageInfoModelList() {
        for (ImageInfoModel imageInfoModel : mDeleteImageInfoModelList) {

            for (ImageInfoResultModel imageInfoResultModel : new ArrayList<>(o_sList)) {
                if (imageInfoResultModel.getData().equals(imageInfoModel.getData())) {
                    o_sList.remove(imageInfoResultModel);
                }
            }
            for (ImageInfoResultModel imageInfoResultModel : new ArrayList<>(nonList)) {
                if (imageInfoResultModel.getData().equals(imageInfoModel.getData())) {
                    nonList.remove(imageInfoResultModel);
                }
            }
            for (ImageInfoResultModel imageInfoResultModel : new ArrayList<>(objectList)) {
                if (imageInfoResultModel.getData().equals(imageInfoModel.getData())) {
                    objectList.remove(imageInfoResultModel);
                }
            }
            for (ImageInfoResultModel imageInfoResultModel : new ArrayList<>(subjectList)) {
                if (imageInfoResultModel.getData().equals(imageInfoModel.getData())) {
                    subjectList.remove(imageInfoResultModel);
                }
            }
        }
        mDeleteImageInfoModelList = null;
        notifyDataSetChanged();
    }

    void removeModelsFromImageInfoResultModelList() {

        for (ImageInfoResultModel imageInfoResultModel : mDeleteImageInfoResultModelList) {
            if (o_sList.contains(imageInfoResultModel))
                o_sList.remove(imageInfoResultModel);
            if (objectList.contains(imageInfoResultModel))
                objectList.remove(imageInfoResultModel);
            if (subjectList.contains(imageInfoResultModel))
                subjectList.remove(imageInfoResultModel);
            if (nonList.contains(imageInfoResultModel))
                nonList.remove(imageInfoResultModel);
        }

        if (CommonUtils.CheckWrite_External_Permission(mContext)) {
            ContentResolver contentResolver = mContext.getContentResolver();
            for (int i = 0; i < mDeleteImageInfoResultModelList.size(); i++)
                contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Images.ImageColumns.DATA + "=?", new String[]{mDeleteImageInfoResultModelList.get(i).getData()});
        }

        mDeleteImageInfoResultModelList.clear();
        notifyDataSetChanged();

        Toast.makeText(mContext, "사진들이 삭제 되었습니다", Toast.LENGTH_SHORT).show();
    }

    int getDeleteImageInfoResultModelListSize() {
        return mDeleteImageInfoResultModelList.size();
    }

    interface ItemClickListener {
        void onItemClick(ImageInfoResultModel imageInfoResultModel);
    }

    void clearDeleteInfoResultModelList() {
        mDeleteImageInfoResultModelList.clear();
    }

    void changeImageDeleteInfoResultModelList(ImageInfoResultModel imageInfoResultModel) {
        if (mDeleteImageInfoResultModelList.contains(imageInfoResultModel)) {
            nowList.get(nowList.indexOf(imageInfoResultModel)).isChecked = false;
            mDeleteImageInfoResultModelList.remove(imageInfoResultModel);
        } else {
            nowList.get(nowList.indexOf(imageInfoResultModel)).isChecked = true;
            mDeleteImageInfoResultModelList.add(imageInfoResultModel);
        }
    }

    void changeDeleteModelsCheckState(boolean bool) {
        for (ImageInfoResultModel imageInfoResultModel : nowList) {
            imageInfoResultModel.isChecked = bool;
        }
    }
//    void changeDeleteModelsCheckState(boolean bool) {
//        for (int i = 0; i < mDeleteImageInfoResultModelList.size(); i ++) {
//            mDeleteImageInfoResultModelList.get(i).isChecked = bool;
//        }
//    }

    void addAllListToDeleteModelList() {
        mDeleteImageInfoResultModelList.addAll(nowList);
    }

    void removeAllListFromDeleteModelList() {
        mDeleteImageInfoResultModelList.clear();
    }
}
