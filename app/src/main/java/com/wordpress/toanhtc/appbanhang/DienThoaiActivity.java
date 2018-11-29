package com.wordpress.toanhtc.appbanhang;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;
    View footerview;
    int idloaisp = 0;
    int page = 1;
    String URL_SANPHAM= "http://dpsg.000webhostapp.com/sever/getsanpham.php?page=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            GetIDLoaiSp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }
        else {
            CheckConnection.showToast_Connect(getApplicationContext(), "Check your internet");
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void LoadMoreData() {
        listViewdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsp", mangdienthoai.get(i));
                startActivity(intent);
            }
        });
        listViewdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if(FirstItem + VisibleItem == TotalItem && TotalItem !=0  && isLoading == false && limitData == false)
                {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
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
                if(response != null && response.length() > 2 )
                {
                    listViewdt.removeFooterView(footerview);
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
                else
                {
                    limitData = true;
                    listViewdt.removeFooterView(footerview);
                    CheckConnection.showToast_Connect(getApplicationContext(),"Hết dữ liệu sản phẩm!");
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
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progress_bar, null);
        mHandler = new mHandler();
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    listViewdt.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);

        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
