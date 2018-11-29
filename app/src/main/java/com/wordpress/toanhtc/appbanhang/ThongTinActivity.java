package com.wordpress.toanhtc.appbanhang;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThongTinActivity extends AppCompatActivity {
    public static String URL_DONHANG = "http://dpsg.000webhostapp.com/sever/thongtinkhachhang.php";
    public static String URL_CHITIETDONHANG = "http://dpsg.000webhostapp.com/sever/chitietdonhang.php";
    public static String nameKh ,phoneKh , emailKh;
    Button save, cancel;
    EditText namekh, phonekh, emailkh;
    ProgressBar load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            CancelEvent();
            ButtonSaveEvent();
        }
        else {
            CheckConnection.showToast_Connect(getApplicationContext(),"Check your Internet");
        }
    }

    private void ButtonSaveEvent() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveEvent();
            }
        });
    }
    public boolean isValidEmail(String target) {
        if (target.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
            return true;
        return false;
    }
    public boolean checkNumberPhone(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return false;
        } else
        if (number.length() == 10 || number.length() == 11) {
            if (number.length() == 10) {
                if (number.substring(0, 2).equals("09")) {
                    return true;
                } else {
                    return false;
                }
            } else
            if (number.substring(0, 2).equals("01")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    private void SaveEvent() {
        load.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        final String name, phone, email;
        name = namekh.getText().toString().trim();
        phone = phonekh.getText().toString().trim();
        email = emailkh.getText().toString().trim();
        boolean error = false;
        if (TextUtils.isEmpty(name))
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            namekh.requestFocus();
            namekh.setError("Vui lòng nhập đầy đủ");
            error = true;
        }

        if (TextUtils.isEmpty(phone))
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            phonekh.requestFocus();
            phonekh.setError("Vui lòng nhập đầy đủ");
            error = true;
        }
        if (checkNumberPhone(phone) == false)
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            phonekh.requestFocus();
            phonekh.setError("Số điện thoại không đúng!");
            error = true;
        }
        if (TextUtils.isEmpty(email))
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            emailkh.requestFocus();
            emailkh.setError("Vui lòng nhập đầy đủ");
            error = true;
        }
        if (isValidEmail(email) == false)
        {
            load.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            emailkh.requestFocus();
            emailkh.setError("Email không hợp lệ");
            error = true;
        }
        if(error == false)
        {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DONHANG, new Response.Listener<String>() {
                @Override
                public void onResponse(final String madonhang) {
                    Log.d("madonhang", madonhang);
                   if (Integer.parseInt(madonhang) > 0 )
                   {
                       RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                       StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL_CHITIETDONHANG, new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                                if (response.equals("1"))
                                {
                                    nameKh = name;
                                    phoneKh = phone;
                                    emailKh = email;
                                    ThongtinKhachhang.SetInfor();
                                    MainActivity.manggiohang.clear();
                                    Toast.makeText(getApplicationContext(), "Thêm thông tin khách hàng thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                                }
                           }
                       }, new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {

                           }
                       }){
                           @Override
                           protected Map<String, String> getParams() throws AuthFailureError {
                               JSONArray jsonArray = new JSONArray();
                               for(int i = 0 ; i< MainActivity.manggiohang.size(); i++)
                               {
                                   JSONObject jsonObject = new JSONObject();
                                   try {
                                       jsonObject.put("madonhang", madonhang);
                                       jsonObject.put("masanpham", MainActivity.manggiohang.get(i).getIdsp());
                                       jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
                                       jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiasp());
                                       jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluongsp());
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                                   jsonArray.put(jsonObject);
                               }
                               HashMap<String, String> hashMap = new HashMap<String, String>();
                               hashMap.put("json", jsonArray.toString());
                               return hashMap;
                           }
                       };
                       requestQueue1.add(stringRequest1);
                   }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("tenkh",name);
                    hashMap.put("sodt",phone);
                    hashMap.put("email",email);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);

        }
    }

    private void CancelEvent() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        namekh = (EditText) findViewById(R.id.edtTenKh);
        phonekh = (EditText) findViewById(R.id.edtPhoneKh);
        emailkh = (EditText) findViewById(R.id.edtEmailKh);
        save = (Button) findViewById(R.id.btnSaveKh);
        cancel = (Button) findViewById(R.id.btnCancelKh);
        load = (ProgressBar) findViewById(R.id.loadingKh);
    }
}
