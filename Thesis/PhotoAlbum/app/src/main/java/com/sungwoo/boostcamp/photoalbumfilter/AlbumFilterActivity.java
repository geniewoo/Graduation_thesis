package com.sungwoo.boostcamp.photoalbumfilter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sungwoo.boostcamp.photoalbumfilter.TensorflowInterface.INPUT_SIZE;

public class AlbumFilterActivity extends AppCompatActivity {
    private TensorflowInterface mTensorflowInterface;
    private static final int REQUEST_IMAGE_GET = 1000;

    @BindView(R.id.targetResultTextView)
    TextView mTargetResultTextView;
    @BindView(R.id.targetResultImageView)
    ImageView mTargetImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_filter);
        ButterKnife.bind(this);
        mTensorflowInterface = new TensorflowInterface(getAssets());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("힝", "여기는 당연하고");
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Log.i("힝", "여기와야해");
            Uri fullPhotoUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
            mTargetImageView.setImageBitmap(bitmap);

            int result = mTensorflowInterface.evaluateImageByModel(bitmap);

            mTargetResultTextView.setText(String.valueOf(result));
            // Do work with photo saved at fullPhotoUri
        }
    }

    @OnClick(R.id.targetResultImageView)
    public void onTargetResultImageViewClick() {
        Log.e("음", "눌림");
        selectImage();
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }
}
