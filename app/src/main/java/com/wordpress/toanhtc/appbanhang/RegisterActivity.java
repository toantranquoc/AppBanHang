package com.wordpress.toanhtc.appbanhang;

import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText email, password, confirmpass;
    Button register;
    ProgressBar loading;
    static String URL_REGIST = "http://192.168.1.3:8888/sever/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regist();
            }
        });
    }

    private void AnhXa() {
        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPass);
        confirmpass = (EditText) findViewById(R.id.edtConfirmPass);
        register = (Button) findViewById(R.id.btnRegister);
        loading = (ProgressBar) findViewById(R.id.loading);
    }
    private void Regist()
    {
        loading.setVisibility(View.VISIBLE);
        register.setVisibility(View.GONE);
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            try
            {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                if (success.equals("1"))
                {
                    Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Register Error! " +e.toString(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password", password);
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this );
        requestQueue.add(stringRequest);
    }

}
