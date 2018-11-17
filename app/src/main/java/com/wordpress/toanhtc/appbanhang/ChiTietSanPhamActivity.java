package com.wordpress.toanhtc.appbanhang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarChitiet;
    ImageView imageViewChitiet;
    TextView tenChitiet, giaChitiet,motaChitiet;
    Button themGiohang;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        AnhXa();
        ActionToolBar();
        GetInformation();
        CatchEventSpinner();
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        int id = 0;
        String tenChitietsp= "";
        int giaChitietsp = 0;
        String hinhanhChitietsp = "";
        String motaChitietsp = "";
        int idSanphamsp = 0;
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsp");
        id = sanPham.getID();
        tenChitietsp = sanPham.getTensanpham();
        giaChitietsp = sanPham.getGiasanpham();
        hinhanhChitietsp = sanPham.getHinhanhsanpham();
        motaChitietsp = sanPham.getMotasanpham();
        idSanphamsp = sanPham.getIDloaisanpham();
        tenChitiet.setText(tenChitietsp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giaChitiet.setText("Giá: "+ decimalFormat.format(giaChitietsp) + " Đ");
        motaChitiet.setText(motaChitietsp);
        Picasso.with(getApplicationContext()).load(hinhanhChitietsp)
                .placeholder(R.drawable.noimageicon)
                .error(R.drawable.erroricon)
                .into(imageViewChitiet);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarChitiet = (Toolbar) findViewById(R.id.toolbarChitietsanpham);
        imageViewChitiet = (ImageView) findViewById(R.id.imageviewChitietsp);
        tenChitiet = (TextView) findViewById(R.id.txtTenChiTietSp);
        giaChitiet  = (TextView) findViewById(R.id.txtGiaChitietsp);
        motaChitiet = (TextView) findViewById(R.id.txtMotachitietsp);
        themGiohang = (Button) findViewById(R.id.btnThemGioHang);
        spinner = (Spinner) findViewById(R.id.spinnerChitietsp);
    }
}
