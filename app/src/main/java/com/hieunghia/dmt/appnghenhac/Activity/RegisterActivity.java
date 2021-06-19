package com.hieunghia.dmt.appnghenhac.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.hieunghia.dmt.appnghenhac.R;
import com.hieunghia.dmt.appnghenhac.Service.APIService;
import com.hieunghia.dmt.appnghenhac.Service.DataService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName, edtPassword, edtEmail, edtPhonNumber, edtRePassWord;
    CircularProgressButton crpRegister;
    String userName, userPassWord, userEmail, userPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        Init();
        checkValue();
        evenClick();
    }

    private void checkValue() {
        edtPhonNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!validateMobile(edtPhonNumber.getText().toString())){
                    edtPhonNumber.setError("Invalid Mobile No");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!validateEmail(edtEmail.getText().toString().trim())){
                    edtEmail.setError("Invalid Email");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!validatePassWord(edtPassword.getText().toString().trim())){
                    edtPassword.setError("Password must be longer 6 characters");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtRePassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!validateRePassWord(edtPassword.getText().toString().trim())){
                    edtRePassWord.setError("Wrong PassWord");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void evenClick() {
        crpRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = edtName.getText().toString();
                userPassWord = edtPassword.getText().toString();
                userEmail = edtEmail.getText().toString();
                userPhoneNumber = edtPhonNumber.getText().toString();
                if (userName.length() > 0 && userPassWord.length() > 0 && userEmail.length() > 0 && userPhoneNumber.length() > 0)
                {
                    DataService dataService = APIService.GetUserAccount();
                    retrofit2.Call<String> callback = dataService.InsertData(userName,userPassWord,userEmail,userPhoneNumber);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            if (result.equals("Success")){
                                Toast.makeText(RegisterActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else if (result.equals("UserNameHaveBeenUsed")){
                                Toast.makeText(RegisterActivity.this, "     Tài khoản đã đăng kí \n  Xin hãy đăng kí tên tài khoản khác", Toast.LENGTH_SHORT).show();
                            }
                            else if (result.equals("EmailHaveBeenUsed")){
                                Toast.makeText(RegisterActivity.this, "         Email đã đăng kí \n  Xin hãy đăng kí tên tài khoản khác", Toast.LENGTH_SHORT).show();
                            }
                        }


                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
                else {
                    Toast.makeText(RegisterActivity.this,"Hãy nhập đủ thông tin!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    private void Init() {
        edtRePassWord = findViewById(R.id.editTextRePassword);
        edtName = findViewById(R.id.editTextName);
        edtPassword = findViewById(R.id.editTextPassword);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPhonNumber = findViewById(R.id.editTextMobile);
        crpRegister = findViewById(R.id.cirRegisterButton);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.pink));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public boolean validateMobile(String input){
        Pattern pattern = Pattern.compile("[0][0-9]{9}");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    public boolean validateEmail(String input){
        String emailpatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!input.matches(emailpatterns))
        {
            return false;
        }else{
            return true;
        }
    }
    public boolean validatePassWord(String input){
        if (input.length() >= 6){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean validateRePassWord(String input){
        if (input.equals(edtRePassWord.getText().toString().trim())){
            return true;
        }
        else {
            return false;
        }
    }
}