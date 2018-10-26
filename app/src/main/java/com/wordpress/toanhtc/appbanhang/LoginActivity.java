package com.wordpress.toanhtc.appbanhang;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity {
    RelativeLayout rellay1, rellay2;
    Button tranRegis, facebook, signin;
    EditText user, password;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000);
        AnhXa();
        TrantoRegister();
    }

    private void TrantoRegister() {
        final Intent intent = new Intent(this , RegisterActivity.class);
        tranRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        tranRegis = (Button) findViewById(R.id.btnTranRegist);
        signin = (Button) findViewById(R.id.btnLogin);
        facebook = (Button) findViewById(R.id.btnLoginFacebook);
        user  =(EditText) findViewById(R.id.ediLoginUser);
        password = (EditText) findViewById(R.id.edtLoginPassword);

    }

}
