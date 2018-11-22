package com.wordpress.toanhtc.appbanhang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ThongtinKhachhang extends AppCompatActivity {
    Toolbar toolbarthongtin;
    static TextView namekh, phonekh, emailkh;
    static TextView thongbao;
    static ImageView haName, haPhone,haEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_khachhang);
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            ActionToolbar();
            SetInfor();
        }
        else
        {
            CheckConnection.showToast_Connect(getApplicationContext(),"Check your Internet");
        }
    }

    public static void SetInfor() {
        if(TextUtils.isEmpty(ThongTinActivity.emailKh) || TextUtils.isEmpty(ThongTinActivity.nameKh) || TextUtils.isEmpty(ThongTinActivity.phoneKh))
        {
            namekh.setVisibility(View.GONE);
            phonekh.setVisibility(View.GONE);
            emailkh.setVisibility(View.GONE);
            haPhone.setVisibility(View.GONE);
            haEmail.setVisibility(View.GONE);
            haName.setVisibility(View.GONE);
            thongbao.setVisibility(View.VISIBLE);

        }
        else {
            namekh.setText(ThongTinActivity.nameKh);
            phonekh.setText(ThongTinActivity.phoneKh);
            emailkh.setText(ThongTinActivity.emailKh);
            namekh.setVisibility(View.VISIBLE);
            phonekh.setVisibility(View.VISIBLE);
            emailkh.setVisibility(View.VISIBLE);
            haPhone.setVisibility(View.VISIBLE);
            haEmail.setVisibility(View.VISIBLE);
            haName.setVisibility(View.VISIBLE);
            thongbao.setVisibility(View.GONE);
        }

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarthongtin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarthongtin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
    private void AnhXa() {
        toolbarthongtin = (Toolbar) findViewById(R.id.toolbarThongtin);
        namekh = (TextView) findViewById(R.id.txtTenThongtin);
        phonekh = (TextView) findViewById(R.id.txtPhoneThongtin);
        emailkh = (TextView) findViewById(R.id.txtEmailThongtin);
        thongbao = (TextView)findViewById(R.id.txtThongtinKhTrong);
        haName = (ImageView) findViewById(R.id.imgNameKh);
        haEmail = (ImageView) findViewById(R.id.imgEmailKh);
        haPhone = (ImageView) findViewById(R.id.imgPhoneKh);
    }
}
