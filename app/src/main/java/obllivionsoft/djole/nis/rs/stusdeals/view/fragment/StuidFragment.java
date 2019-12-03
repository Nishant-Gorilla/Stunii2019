package obllivionsoft.djole.nis.rs.stusdeals.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppConstant;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.AppPreferences;
import obllivionsoft.djole.nis.rs.stusdeals.controller.Utills.ProgressBar;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiClient;
import obllivionsoft.djole.nis.rs.stusdeals.controller.apis.ApiInterface;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextView;
import obllivionsoft.djole.nis.rs.stusdeals.controller.customViews.MyTextViewBold;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.GetImageResponse;
import obllivionsoft.djole.nis.rs.stusdeals.model.ResponseModel.StuIDResponseModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class StuidFragment extends Fragment {
    View rootView;
    @BindView(R.id.ll_verified)
    LinearLayout llVerified;
    @BindView(R.id.ll_back)
    RelativeLayout llBack;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.img_edit)
    AppCompatImageView imgEdit;
    @BindView(R.id.tv_name)
    MyTextViewBold tvName;
    @BindView(R.id.tv_university)
    MyTextView tvUniversity;
    @BindView(R.id.tv_verified_date)
    MyTextView tvVerifiedDate;
    @BindView(R.id.tv_course)
    MyTextView tvCourse;
    private static final int REQ_CAMERA_IMAGE = 2;
    private static final int PICK_FROM_GALLARY = 1;
    File mImagefile;
    String mUserid="",mAccessToken="";
    Unbinder unbinder;
    String mName="",mUniversity="",mVerified="",mCourse="",mImage="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_stuid, container, false);
        }
        unbinder = ButterKnife.bind(this, rootView);
        initview();
        return rootView;
    }

    private void initview() {

        mUserid = AppPreferences.init(getActivity()).getString(AppConstant.USER_ID);
        mAccessToken = AppPreferences.init(getActivity()).getString(AppConstant.ACCESS_TOKEN);
        Log.e("token",mAccessToken+"");
        getStudidInfo();
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>get stu is info>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void getStudidInfo() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<StuIDResponseModel> call = apiService.stuIdInfo("students/" + mUserid,"application/json","YW5kcm9pZF9hcHA6MzA1MEI3V1QwVmoz",mAccessToken);
        call.enqueue(new Callback<StuIDResponseModel>() {
            @Override
            public void onResponse(Call<StuIDResponseModel> call, Response<StuIDResponseModel> response) {
                try {
                    ProgressBar.getInstanse().hideDialog();
                    if (response.body() != null) {
                        mName=response.body().fname+" "+response.body().lname;
                        String verifiedUntil =response.body().created_at;
                        mVerified = response.body().graduationDate;
                        mUniversity=response.body().institution;
                        mCourse=response.body().course;
                        mImage=response.body().photo;
                        setData();


                    }
                } catch (Exception e) {
                    ProgressBar.getInstanse().hideDialog();
                }

            }

            @Override
            public void onFailure(Call<StuIDResponseModel> call, Throwable t) {
                ProgressBar.getInstanse().hideDialog();
            }


        });


    }

    private String GetVerifiedUntilDate(String verifiedUntil) {


        SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.tsFromServer);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormat.parse(verifiedUntil));
            cal.add(Calendar.YEAR, 5);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "Verified until " + displayFormat.format(cal.getTime());
    }



    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>set data of stu  id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void setData() {

        tvName.setText(mName);
        if(!mVerified.isEmpty())
        {
            tvVerifiedDate.setText("Verified until "+mVerified);
        }

        tvUniversity.setText(mUniversity);
        tvCourse.setText(mCourse);
        if(!mImage.isEmpty())
        {
            Glide.with(getActivity())
                    .load(AppConstant.imgStudentUrl + mUserid + "/o/" + mImage)
                    .into(imgProfile);
        }
    }

    @OnClick(R.id.img_edit)
    public void onViewClicked() {
        showdialog();

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>image pic from camera and gallery>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void showdialog() {

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.app_name))
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        takePicture();
                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        pickImageSingle();
                    }


                })
                .setIcon(R.drawable.logosignup)
                .show();
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>image from gallery>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void pickImageSingle() {

        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_FROM_GALLARY);
        } else {
            EasyPermissions.requestPermissions(this, "Need Permission to access your Gallery and Camera",
                    PICK_FROM_GALLARY, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>image from camera>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private void takePicture() {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQ_CAMERA_IMAGE);

        } else {
            EasyPermissions.requestPermissions(this, "Need Permission to access your Gallery and Camera",
                    REQ_CAMERA_IMAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        }


    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>on activity result for camera and gallery>>>>>>>>>>>>>>>>>>>>>>>>>

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CAMERA_IMAGE:
                if (resultCode == RESULT_OK) {

                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Uri tempUri = getImageUri(getContext(), photo);
                    mImagefile = new File(getRealPathFromURI(tempUri));
                    uploadimage();
                }
                break;

            case PICK_FROM_GALLARY:

                if (resultCode == RESULT_OK) {


                    Uri selectedImage = data.getData();
                    mImagefile = new File(getRealPathFromURI(selectedImage));
                    uploadimage();


                }
        }
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>upload image>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void uploadimage() {

           ProgressBar.getInstanse().showDialog(getActivity());
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("photo", mImagefile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), mImagefile));
            MultipartBody requestBody = builder.build();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Log.e("requestbody", requestBody + "");
            Call<GetImageResponse> call = apiService.uploadStuid(mUserid,requestBody);
            call.enqueue(new Callback<GetImageResponse>() {
                @Override
                public void onResponse(Call<GetImageResponse> call, Response<GetImageResponse> response) {
                    try {
                        ProgressBar.getInstanse().hideDialog();
                        if (response.body() != null) {

                            initview();
                        }

                    } catch (Exception e) {
                        ProgressBar.getInstanse().hideDialog();

                    }

                }

                @Override
                public void onFailure(Call<GetImageResponse> call, Throwable t) {
              ProgressBar.getInstanse().hideDialog();

                }
            });


        }





    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        @SuppressLint("Recycle") Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
