package com.hieunghia.dmt.appnghenhac.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContentResolverCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.hieunghia.dmt.appnghenhac.Activity.LoginActivity;
import com.hieunghia.dmt.appnghenhac.Activity.MainActivity;
import com.hieunghia.dmt.appnghenhac.Model.User;
import com.hieunghia.dmt.appnghenhac.R;
import com.hieunghia.dmt.appnghenhac.Service.APIService;
import com.hieunghia.dmt.appnghenhac.Service.DataService;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static android.app.Activity.RESULT_OK;
import static com.hieunghia.dmt.appnghenhac.Activity.LoginActivity.isAvatarNull;
import static com.hieunghia.dmt.appnghenhac.Activity.LoginActivity.mGoogleSignInClient;

public class Fragment_Ho_So extends Fragment {
    View view;
    TextView txtName,txtPhone,txtEmail;
    CircularProgressButton crpbLogout;
    MainActivity mainActivity;
    CircleImageView circleImageView;
    MaterialButton btnEditImage;
    int RequestAvatar = 123;
    Bitmap bitmap;
    User user;
    String realPath = "";
    Context context;
    String file_path;
    File file;
    public Fragment_Ho_So(Context context){
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ho_so, container, false);
        mainActivity = (MainActivity) getActivity();

        init();
        upDateValue();
        crpbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOut();
            }
        });
        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageUpload(RequestAvatar);
            }
        });

        return view;
    }

    private void SignOut() {
        if (mGoogleSignInClient != null){
            mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    Toast.makeText(mainActivity, "đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            });
        }
        else {
            mainActivity.finish();
        }
    }

    private void SelectImageUpload(int code) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, code);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestAvatar && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            splitRealPath(path);
            uploadimageToFolder(file);

            try {
                InputStream inputStream = mainActivity.getContentResolver().openInputStream(path);
                bitmap = BitmapFactory.decodeStream(inputStream);
                circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void splitRealPath(Uri path) {
        realPath = getRealPathFromURI(path);
        file = new File(realPath);
        file_path = file.getAbsolutePath();
        String[] mangtenfile = file_path.split("\\.");
        file_path = mangtenfile[0] + System.currentTimeMillis() +"."+mangtenfile[1];
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = mainActivity.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }



    private void uploadimageToFolder(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);
        DataService dataService = APIService.GetUserAccount();
        Call<String> callback = dataService.UploadPhoto(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null){
                    String message = response.body();
                    String hinhanh = APIService.user_url + "image/" + message;
                    Log.d("BBB",hinhanh);
                    if (message.length() > 0){
                        DataService dataService1 = APIService.GetUserAccount();
                        Call<String> callback = dataService1.UploadPhotoToUser(mainActivity.getGetEmail(),hinhanh);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String result = response.body();
                                if (result.equals("Success")){
                                    Log.d("BBB",hinhanh);
                                    circleImageView.setImageBitmap(bitmap);
                                    Toast.makeText(mainActivity, "Đã Chỉnh sửa ảnh!", Toast.LENGTH_SHORT).show();
                                    Log.d("BBB","Đã Chỉnh sửa ảnh!");
                                }else if(result.equals("null")){
                                    Toast.makeText(mainActivity, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                                    Log.d("BBB","không tồn tại");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(mainActivity, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                                Log.d("BBB","Lỗi kết nối1");
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(mainActivity, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                Log.d("BBB","Lỗi kết nối 2");
            }
        });
    }

    private void init() {
        btnEditImage = view.findViewById(R.id.btneditimage);
        txtName = view.findViewById(R.id.txtusername);
        txtEmail = view.findViewById(R.id.textviewemail);
        txtPhone = view.findViewById(R.id.textviewphone);
        circleImageView = view.findViewById(R.id.circleImageView);
        crpbLogout = view.findViewById(R.id.cirProfileLogoutButton);
    }

    private void upDateValue() {
        txtEmail.setText(mainActivity.getGetEmail().trim());
        txtPhone.setText(mainActivity.getGetPhone());
        txtName.setText(mainActivity.getGetName().trim());
        if (!isAvatarNull){
            Picasso.with(context).load(mainActivity.getGetPersonImage()).into(circleImageView);
            Log.d("BBB",String.valueOf(isAvatarNull));
        }
    }
}
