package com.hieunghia.dmt.appnghenhac.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hieunghia.dmt.appnghenhac.Model.User;
import com.hieunghia.dmt.appnghenhac.R;
import com.hieunghia.dmt.appnghenhac.Service.APIService;
import com.hieunghia.dmt.appnghenhac.Service.DataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassWordActivity extends AppCompatActivity {

    EditText edtResetPassWord;
    CircularProgressButton crpbReset;
    TextView txtResetPassword;
    String email;
    String getPassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Init();
        crpbReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtResetPassWord.getText().toString();
                Log.d("BBB", email);

                if (email.length() > 0){
                    DataService dataService = APIService.GetUserAccount();
                    Call<List<User>> callback = dataService.GetForgetPassWord(email);
                    callback.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            ArrayList<User> arrayList = (ArrayList<User>) response.body();
                            txtResetPassword.setVisibility(View.VISIBLE);
                            txtResetPassword.setText("Mật khẩu của bạn là: " + arrayList.get(0).getMatKhau());
                            Toast.makeText(ForgetPassWordActivity.this, "Lấy mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(ForgetPassWordActivity.this, "Email không tồn tại!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    Toast.makeText(ForgetPassWordActivity.this, "Hãy nhập Email!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void Init() {
        txtResetPassword = findViewById(R.id.txtTakePassWord);
        crpbReset = findViewById(R.id.cirResetButton);
        edtResetPassWord = findViewById(R.id.edittextResetPassWord);
    }

    public void onLoginClick(View view){
        finish();
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

}