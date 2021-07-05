package com.hieunghia.dmt.appnghenhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hieunghia.dmt.appnghenhac.Model.User;
import com.hieunghia.dmt.appnghenhac.R;
import com.hieunghia.dmt.appnghenhac.Service.APIService;
import com.hieunghia.dmt.appnghenhac.Service.DataService;
import com.hieunghia.dmt.appnghenhac.fragment.Fragment_Ho_So;
import com.hieunghia.dmt.appnghenhac.fragment.Fragment_Trang_Chu;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    EditText edtUserName,edtPassWord;
    ImageView imgView;
    Button btnGoogle, btnNoWiFi;
    CircularProgressButton crpLogin;
    String email,passWord;
    TextView txtforgetPass, txtnewUser, txtanotherMethods;
    RelativeLayout relativeLayout;
    public static GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    String FaceBookName,FaceBookEmail;
    Uri imgProfile;
    public static boolean isAvatarNull = true;
    public static Boolean isLogByGoogle = true;
    public static SharedPreferences sharedPreferences;
    public static ArrayList<User> arrUser;


    public static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);

        btnNoWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithoutWifi();
            }
        });

        crpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtUserName.getText().toString();
                passWord = edtPassWord.getText().toString();
                if (email.length() > 0 && passWord.length() > 0) {
                    DataService dataService = APIService.GetUserAccount();
                    Call<List<User>> callback = dataService.GetLoginData(email,passWord);
                    callback.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            arrUser = (ArrayList<User>) response.body();
                            if (arrUser.size() > 0)
                            {
                                isLogByGoogle = false;
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("UserName",arrUser.get(0).getTaiKhoan());
                                intent.putExtra("UserEmail",arrUser.get(0).getEmail());
                                intent.putExtra("UserPhone",arrUser.get(0).getSoDienThoai());
                                if(!arrUser.get(0).getHinhanh().equals("")){
                                    isAvatarNull = false;
                                    intent.putExtra("UserImage",arrUser.get(0).getHinhanh());
                                }
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Tài Khoản này không tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Xin hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginWithoutWifi(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("nowifi", true);
        startActivity(intent);
    }

    private void signIn() {
        isLogByGoogle = true;
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        } catch (ApiException e) {

        }
    }


    private void Init() {
        btnNoWiFi = findViewById(R.id.loginWithoutWifi);
        edtUserName = findViewById(R.id.edtTextName);
        edtPassWord = findViewById(R.id.edtTextPassword);
        crpLogin = findViewById(R.id.cirLoginButton);
        imgView = findViewById(R.id.imageviewbackgroundlogin);
        txtforgetPass = findViewById(R.id.txtforgetpass);
        txtnewUser = findViewById(R.id.txtnewuser);
        txtanotherMethods = findViewById(R.id.txtanothermethods);
        relativeLayout = findViewById(R.id.relativeLogin);
        sharedPreferences = LoginActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);

    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
    private void AutoLogin() {
        edtUserName.setText(sharedPreferences.getString("username", ""));
        edtPassWord.setText(sharedPreferences.getString("password", ""));
        if (!edtPassWord.getText().toString().equals("") && !edtUserName.getText().toString().equals(""))
        {
//            login();
        }
    }


    public String getFaceBookName() {
        return FaceBookName;
    }

    public String getFaceBookEmail() {
        return FaceBookEmail;
    }

    public Uri getImgProfile() {
        return imgProfile;
    }

    public void onForgetClick(View view) {
        Intent intent = new Intent(this, ForgetPassWordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}