package com.wordpress.toanhtc.appbanhang;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        ActionBar();
        ActionViewFlipper();
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
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
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
        listView = (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout  = (DrawerLayout) findViewById(R.id.drawerlayout);
    }
}
