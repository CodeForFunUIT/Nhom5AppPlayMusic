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
    ImageView imgView,imgFaceBook,imgGoogle;
    CircularProgressButton crpLogin;
    String name,passWord;
    TextView txtforgetPass, txtnewUser, txtanotherMethods;
    RelativeLayout relativeLayout;
    public static GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    LoginButton loginButton ;
    String FaceBookName,FaceBookEmail;
    Uri imgProfile;
    public static boolean isAvatarNull = true;
    public static Boolean isLogByFaceBook = false;
    public static SharedPreferences sharedPreferences;



    public static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));

//         Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        imgFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        crpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edtUserName.getText().toString();
                passWord = edtPassWord.getText().toString();
                if (name.length() > 0 && passWord.length() > 0) {
                    DataService dataService = APIService.GetUserAccount();
                    Call<List<User>> callback = dataService.GetLoginData(name,passWord);
                    callback.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            ArrayList<User> arrUser = (ArrayList<User>) response.body();
                            if (arrUser.size() > 0)
                            {
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

    private void login(){

    }

    private void getDataFaceBook() {
        GraphRequest graphRequest =  GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Toast.makeText(LoginActivity.this, "hello", Toast.LENGTH_SHORT).show();
                            FaceBookName = object.getString("name");
                            FaceBookEmail = object.getString("email");
                            imgProfile = Uri.parse("https://graph.facebook.com/" + object.getString("id") + "/picture?type=large");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle bundle = new Bundle();
        bundle.putString("fields","email, name , id");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else {
            callbackManager.onActivityResult(requestCode,resultCode,data);

        }
    }
//    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//            if (currentAccessToken == null){
//                LoginManager.getInstance().logOut();
//            }
//        }
//    };
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        accessTokenTracker.stopTracking();
//    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        } catch (ApiException e) {

        }
    }


    private void Init() {
        loginButton =(LoginButton) findViewById(R.id.login_button);
        edtUserName = findViewById(R.id.edtTextName);
        edtPassWord = findViewById(R.id.edtTextPassword);
        crpLogin = findViewById(R.id.cirLoginButton);
        imgView = findViewById(R.id.imageviewbackgroundlogin);
        imgGoogle = findViewById(R.id.imageviewbackgroundgoogle);
        imgFaceBook = findViewById(R.id.imageviewbackgroundfacebook);
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
            login();
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
}