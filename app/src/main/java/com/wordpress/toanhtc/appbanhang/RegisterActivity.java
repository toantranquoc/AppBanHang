package com.wordpress.toanhtc.appbanhang;

import android.accounts.Account;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText user, password, confirmpass;
    Button register,cancel;
    ProgressBar loading;
    public static final String REGISTER_URL = "http://192.168.1.3:8888/sever/register.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Regist();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        else
        {
            CheckConnection.showToast_Connect(getApplicationContext(), getApplicationContext().getResources().getString(R.string.check_connect));
        }
    }

    private void AnhXa() {
        user = (EditText) findViewById(R.id.edtUser);
        password = (EditText) findViewById(R.id.edtPass);
        confirmpass = (EditText) findViewById(R.id.edtConfirmPass);
        register = (Button) findViewById(R.id.btnRegister);
        loading = (ProgressBar) findViewById(R.id.loading);
        cancel  = (Button) findViewById(R.id.btnBack);
    }
    private void Regist() {
        boolean error = false;
        loading.setVisibility(View.VISIBLE);
        register.setVisibility(View.GONE);
        final String User = this.user.getText().toString().trim();
        final String Password = this.password.getText().toString().trim();
        final String ConPass = this.confirmpass.getText().toString().trim();
        if (TextUtils.isEmpty(User)) {
            user.requestFocus();
            user.setError(getApplicationContext().getResources().getString(R.string.require_infor));
            loading.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);
            error = true;

        }
        if (TextUtils.isEmpty(Password)) {
            password.requestFocus();
            password.setError(getApplicationContext().getResources().getString(R.string.require_infor));
            error = true;
            loading.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);

        }
        if (!Password.equals(ConPass)) {
            confirmpass.requestFocus();
            confirmpass.setError(getApplicationContext().getResources().getString(R.string.pass_match));
            error = true;
            loading.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);
        }
        if(Password.length() < 8)
        {
            password.requestFocus();
            password.setError(getApplicationContext().getResources().getString(R.string.regis_tooshort));
            error = true;
            loading.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);
        }
        if (!error) {
            StringRequest registerRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String mess = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getInt("success") == 1)
                                {
                                    mess = jsonObject.getString("message");
                                    Toast.makeText(getApplicationContext(),  mess , Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra(LoginActivity.USER_NAME_LOGIN, User);
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }
                                else{
                                    mess = jsonObject.getString("message");
                                    Toast.makeText(getApplicationContext(),mess, Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    register.setVisibility(View.VISIBLE);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(KEY_USERNAME, User);
                    params.put(KEY_PASSWORD, Password);
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(registerRequest);
        }
    }
}
