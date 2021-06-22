package com.hieunghia.dmt.appnghenhac.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hieunghia.dmt.appnghenhac.Activity.LoginActivity;
import com.hieunghia.dmt.appnghenhac.Activity.MainActivity;
import com.hieunghia.dmt.appnghenhac.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hieunghia.dmt.appnghenhac.Activity.LoginActivity.mGoogleSignInClient;

public class Fragment_Ho_So extends Fragment {
    View view;
    TextView txtName,txtPhone,txtEmail;
    CircularProgressButton crpbLogout;
    MainActivity mainActivity;
    CircleImageView circleImageView;

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

    private void init() {
        txtName = view.findViewById(R.id.txtusername);
        txtEmail = view.findViewById(R.id.textviewemail);
        txtPhone = view.findViewById(R.id.textviewphone);
        circleImageView = view.findViewById(R.id.circleImageView);
        crpbLogout = view.findViewById(R.id.cirProfileLogoutButton);
    }

    private void upDateValue() {

        txtEmail.setText(mainActivity.getGetEmail());
        txtPhone.setText(mainActivity.getGetPhone());
        txtName.setText(mainActivity.getGetName());
        Picasso.with(getActivity()).load(String.valueOf(mainActivity.getPersonPhoto())).into(circleImageView);
    }
}
