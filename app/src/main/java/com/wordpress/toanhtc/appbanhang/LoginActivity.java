package com.wordpress.toanhtc.appbanhang;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    RelativeLayout rellay1, rellay2;
    CallbackManager callbackManager;
    LoginButton loginButton;
    Button tranRegis, signin;
    EditText user, password;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };
    public static final String LOGIN_URL = "http://192.168.1.9:8888/sever/login.php";
    public static  final String USER_NAME_LOGIN = "USER_NAME";
    public static final int REQUEST_CODE_REGISTER = 1;
    public static final String USER_TO_MAIN = "USER_TO_MAIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000);
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            TrantoRegister();
            Login_Facebook();
            signin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log_in();
                }
            });
        }
        else
        {

            CheckConnection.showToast_Connect(getApplicationContext(),getApplicationContext().getResources().getString(R.string.check_connect));
        }



    }

    private void Log_in(){
            final String User = this.user.getText().toString().trim();
            final String Pass = this.password.getText().toString().trim();
            boolean error = false;
            if(TextUtils.isEmpty(User))
            {
                user.requestFocus();
                user.setError(getApplicationContext().getResources().getString(R.string.require_infor));
                error = true;
            }
            if(TextUtils.isEmpty(Pass))
            {
                password.requestFocus();
                password.setError(getApplicationContext().getResources().getString(R.string.require_infor));
                error = true;
            }
            if(!error)
            {
                StringRequest loginRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    String mess="";
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getInt("success" ) == 1)
                                    {
                                        mess = jsonObject.getString("message");
                                        Toast.makeText(getApplicationContext(),getApplication().getResources().getString(R.string.login_success) , Toast.LENGTH_SHORT).show();
                                        Intent main = new Intent(LoginActivity.this, MainActivity.class);
                                        main.putExtra("USER_TO_MAIN", User);
                                        startActivity(main);

                                    }
                                    else
                                    {
                                        mess = jsonObject.getString("message");
                                        Toast.makeText(getApplicationContext(),getApplication().getResources().getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), ""+ error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", User);
                        params.put("password", Pass);
                        return params;
                    }

                };
                RequestQueue requestQueue  = Volley.newRequestQueue(this);
                //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(loginRequest);
            }
        }
    private void Login_Facebook(){
        try {
            final Intent manHinh = new Intent(this, MainActivity.class);
            callbackManager = CallbackManager.Factory.create();
            loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_gender"));
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    startActivity(manHinh);
                    loginButton.setVisibility(View.INVISIBLE);
                    finish();
                    LoginManager.getInstance().logOut();
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.check_connect), Toast.LENGTH_SHORT).show();
        }
    }
    private void TrantoRegister() {
        final Intent regist = new Intent(this , RegisterActivity.class);
        tranRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(regist, REQUEST_CODE_REGISTER);
            }
        });
    }

    private void AnhXa() {
        tranRegis = (Button) findViewById(R.id.btnTranRegist);
        signin = (Button) findViewById(R.id.btnLogin);
        loginButton = (LoginButton) findViewById(R.id.btnLoginFacebook);
        user  =(EditText) findViewById(R.id.ediLoginUser);
        password = (EditText) findViewById(R.id.edtLoginPassword);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE_REGISTER && resultCode == Activity.RESULT_OK)
            {
                String userName = data.getStringExtra(LoginActivity.USER_NAME_LOGIN);
                user.setText(userName);
                password.requestFocus();
            }

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.check_connect), Toast.LENGTH_SHORT).show();
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

}
