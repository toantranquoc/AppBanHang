package com.wordpress.toanhtc.appbanhang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbardt;
    ListView listViewdt;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<SanPham> mangdienthoai;
    int idloaisp = 0;
    int page = 1;
    String URL_SANPHAM= "http://192.168.1.110:8888/sever/getsanpham.php?page=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        AnhXa();
        GetIDLoaiSp();
        ActionToolbar();
        GetData(page);
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = URL_SANPHAM + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tendt = "";
                int giadt = 0;
                String hinhanhdt = "";
                String motadt = "";
                int idspdt = 0;
                if(response != null)
                {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i<jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tendt = jsonObject.getString("tensp");
                            giadt = jsonObject.getInt("giasp");
                            hinhanhdt = jsonObject.getString("hinhanhsp");
                            motadt = jsonObject.getString("motasp");
                            idspdt = jsonObject.getInt("idsanpham");
                            mangdienthoai.add(new SanPham(id,tendt,giadt,hinhanhdt,motadt,idspdt));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(idloaisp));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void GetIDLoaiSp() {
        idloaisp = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisp", idloaisp+"");
    }

    private void AnhXa() {
        toolbardt = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarSanpham);
        listViewdt = (ListView) findViewById(R.id.listviewDienthoai);
        mangdienthoai = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(), mangdienthoai);
        listViewdt.setAdapter(dienThoaiAdapter);
    }
}
