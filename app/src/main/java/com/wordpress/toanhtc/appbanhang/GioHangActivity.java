package com.wordpress.toanhtc.appbanhang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolbardgiohang;
    ListView listViewGiohang;
    TextView thongBao, tongTien;
    Button thanhToan, tieptucMuahang;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionToolbar();
        CheckData();
        EvenUlti();
    }

    private void EvenUlti() {
        long tongtien = 0;
        for(int i = 0; i< MainActivity.manggiohang.size(); i++)
        {
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongTien.setText(decimalFormat.format(tongtien) + " Đ");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <= 0)
        {
            gioHangAdapter.notifyDataSetChanged();
            thongBao.setVisibility(View.VISIBLE);
            listViewGiohang.setVisibility(View.INVISIBLE);
        }
        else
        {
            gioHangAdapter.notifyDataSetChanged();
            thongBao.setVisibility(View.INVISIBLE);
            listViewGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardgiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardgiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbardgiohang = (Toolbar) findViewById(R.id.toolbarGiohang);
        listViewGiohang = (ListView) findViewById(R.id.listviewGiohang);
        thongBao = (TextView) findViewById(R.id.txtGiohangtrong);
        tongTien = (TextView) findViewById(R.id.txtTongGiaTri);
        thanhToan = (Button) findViewById(R.id.btnThanhtoangiohang);
        tieptucMuahang = (Button) findViewById(R.id.btnTieptucmuahang);
        gioHangAdapter = new GioHangAdapter(this, MainActivity.manggiohang);
        listViewGiohang.setAdapter(gioHangAdapter);
    }
}
