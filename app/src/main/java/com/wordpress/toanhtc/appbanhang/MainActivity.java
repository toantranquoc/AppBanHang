package com.wordpress.toanhtc.appbanhang;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<SanPham> mangsanpham;
    SanPhamAdapter sanPhamAdapter;
    public static String URL_NEWSP = "http://192.168.1.6:8888/sever/getspmoinhat.php";
    public static ArrayList<GioHang> manggiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            GetDuLieuSpMoiNhat();
            SelectMenu();

        }
        else
        {
            Toast.makeText(this , getApplication().getResources().getString(R.string.check_connect), Toast.LENGTH_SHORT).show();
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

    private void SelectMenu() {
        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.apple: Toast.makeText(getApplicationContext(), "Apple",Toast.LENGTH_LONG).show();
                        Intent apple = new Intent(MainActivity.this, DienThoaiActivity.class);
                        apple.putExtra("idloaisanpham",1);
                        startActivity(apple);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.samsung: Toast.makeText(getApplicationContext(), "SAMSUNG",Toast.LENGTH_LONG).show();
                        Intent samsung = new Intent(MainActivity.this, DienThoaiActivity.class);
                        samsung.putExtra("idloaisanpham",2);
                        startActivity(samsung);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.oppo: Toast.makeText(getApplicationContext(), "OPPO",Toast.LENGTH_LONG).show();
                        Intent oppo = new Intent(MainActivity.this, DienThoaiActivity.class);
                        oppo.putExtra("idloaisanpham",3);
                        startActivity(oppo);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.xiaomi: Toast.makeText(getApplicationContext(), "XIAOMI",Toast.LENGTH_LONG).show();
                        Intent xiaomi = new Intent(MainActivity.this, DienThoaiActivity.class);
                        xiaomi.putExtra("idloaisanpham",4);
                        startActivity(xiaomi);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.thongtin: Toast.makeText(getApplicationContext(), "Information",Toast.LENGTH_LONG).show();
                        Intent thongtin = new Intent(MainActivity.this, ThongTinActivity.class);
                        startActivity(thongtin);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.contact: Toast.makeText(getApplicationContext(), "Contact",Toast.LENGTH_LONG).show();
                        Intent lienlac = new Intent(MainActivity.this, LienLacActivity.class);
                        startActivity(lienlac);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });
    }

    private void GetDuLieuSpMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_NEWSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!= null)
                {
                    int ID = 0;
                    String Tensp = "";
                    Integer Gia  = 0;
                    String Hinhanhsp ="";
                    String Motasp = "";
                    int Idloaisanpham = 0;
                    for(int i = 0; i<response.length(); i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensp = jsonObject.getString("tensp");
                            Gia = jsonObject.getInt("giasp");
                            Hinhanhsp = jsonObject.getString("hinhanhsp");
                            Motasp = jsonObject.getString("motasp");
                            Idloaisanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new SanPham(ID,Tensp,Gia,Hinhanhsp,Motasp,Idloaisanpham));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), ""+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangQuangcao = new ArrayList<>();
        mangQuangcao.add("https://cdn.tgdd.vn/qcao/15_10_2018_21_25_48_Realme-800-300.png");
        mangQuangcao.add("https://cdn.tgdd.vn/qcao/19_10_2018_06_57_49_iPhoneNEW-800-300.png");
        mangQuangcao.add("https://cdn.tgdd.vn/qcao/23_10_2018_10_17_51_Oppo-f9-tim-800-300.png");
        mangQuangcao.add("https://cdn1.vienthonga.vn/image/2018/10/5/100000_banner-samsung-galaxy-j4plus-j6plus-400x200.jpg");
        mangQuangcao.add("https://cdn1.vienthonga.vn/image/2018/10/1/100000_samsung-j8-400x200.jpg");
        for(int i = 0; i< mangQuangcao.size(); i++)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarTrangchu);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView)findViewById(R.id.navigationview);
        drawerLayout  = (DrawerLayout) findViewById(R.id.drawerlayout);
        mangsanpham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), mangsanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(sanPhamAdapter);
        if(manggiohang != null)
        {}
        else
        {
            manggiohang = new ArrayList<>();
        }
    }
}
